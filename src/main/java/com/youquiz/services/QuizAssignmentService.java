package com.youquiz.services;

import com.youquiz.dto.requestdto.QuizAssignmentRequestDTO;
import com.youquiz.dto.responsedto.QuizAssignmentDTO;
import com.youquiz.entities.*;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.exceptions.ResourceUnprocessableException;
import com.youquiz.repositories.AnswerValidationRepository;
import com.youquiz.repositories.QuizAssignmentRepository;
import com.youquiz.repositories.QuizRepository;
import com.youquiz.repositories.StudentRepository;
import com.youquiz.services.interfaces.IQuizAssignmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class QuizAssignmentService implements IQuizAssignmentService {
    private final QuizAssignmentRepository quizAssignmentRepository;
    private final StudentRepository studentRepository;
    private final QuizRepository quizRepository;
    private final AnswerValidationRepository answerValidationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuizAssignmentService(
            QuizAssignmentRepository quizAssignmentRepository,
            StudentRepository studentRepository,
            QuizRepository quizRepository,
            AnswerValidationRepository answerValidationRepository,
            ModelMapper modelMapper
    ) {
        this.quizAssignmentRepository = quizAssignmentRepository;
        this.studentRepository = studentRepository;
        this.quizRepository = quizRepository;
        this.answerValidationRepository = answerValidationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QuizAssignmentDTO create(QuizAssignmentRequestDTO request) {
        AtomicReference<Short> attempt = new AtomicReference<>((short) 1);
        Optional<QuizAssignment> qa = quizAssignmentRepository
            .findByStudentIdAndQuizId(request.getStudentId(), request.getQuizId());
        Quiz quiz = quizRepository.findById(request.getQuizId())
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found."));
        Student student = studentRepository.findById(request.getStudentId())
            .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        qa.ifPresent(
            quizAssignment -> {
                if (quizAssignment.getAttempt() >= quiz.getChances()) {
                    throw new ResourceUnprocessableException(student.getName() + " " + student.getFamilyName() + " has no more chances for this quiz.");
                }

                attempt.set((short) (quizAssignment.getAttempt() + 1));
            }
        );

        if (request.getStartingTime().isAfter(request.getEndingTime())) {
            throw new ResourceUnprocessableException("Starting time must be before ending time.");
        }

        QuizAssignment qaRequest = modelMapper.map(request, QuizAssignment.class);
        qaRequest.setAttempt(attempt.get());

        QuizAssignment quizAssignment = quizAssignmentRepository.save(qaRequest);

        return modelMapper.map(quizAssignment, QuizAssignmentDTO.class);
    }

    @Override
    public QuizAssignmentDTO update(QuizAssignmentRequestDTO request) {
        return null;
    }

    @Override
    public QuizAssignmentDTO delete(Long id) {
        QuizAssignment quizAssignment = quizAssignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz-assignment not found."));

        quizAssignmentRepository.deleteById(id);

        return modelMapper.map(quizAssignment, QuizAssignmentDTO.class);
    }

    @Override
    public QuizAssignmentDTO get(Long id) {
        QuizAssignment quizAssignment = quizAssignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz-assignment not found."));

        if (!quizAssignment.getQuiz().isCanSeeResult()) {
            quizAssignment.setScore(0);
            quizAssignment.setPassResult(0);
        }

        return modelMapper.map(quizAssignment, QuizAssignmentDTO.class);
    }

    @Override
    public QuizAssignmentDTO getWithResults(Long id) {
        // Get the quiz assignment with its result & score regardless if "CanSeeResult" is true or false.
        QuizAssignment quizAssignment = quizAssignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz-assignment not found."));

        return modelMapper.map(quizAssignment, QuizAssignmentDTO.class);
    }

    @Override
    public Page<QuizAssignmentDTO> getAll(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<QuizAssignmentDTO> getByStudentWithResults(Long studentId) {
        // Get list of quiz assignments with their results & score regardless if "CanSeeResult" is true or false.
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found.");
        }

        List<QuizAssignment> quizAssignments = quizAssignmentRepository.findAllByStudentId(studentId);

        return quizAssignments.stream().map(
            quizAssignment -> modelMapper.map(quizAssignment, QuizAssignmentDTO.class)
        ).toList();
    }

    @Override
    public List<QuizAssignmentDTO> getByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found.");
        }

        List<QuizAssignment> quizAssignments = quizAssignmentRepository.findAllByStudentId(studentId);

        quizAssignments = quizAssignments.stream().peek(
            quizAssignment -> {
                if (!quizAssignment.getQuiz().isCanSeeResult()) {
                    quizAssignment.setScore(0);
                    quizAssignment.setPassResult(0);
                }

            }
        ).toList();

        return quizAssignments.stream().map(
            quizAssignment -> modelMapper.map(quizAssignment, QuizAssignmentDTO.class)
        ).toList();
    }

    @Override
    public QuizAssignmentDTO saveSelectedAnswer(Long answerValidationId, Long quizAssignmentId) {
        AnswerValidation answerValidation = answerValidationRepository.findById(answerValidationId)
            .orElseThrow(() -> new ResourceNotFoundException("Answer-validation not found."));
        QuizAssignment quizAssignment = quizAssignmentRepository.findById(quizAssignmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz-assignment not found."));

        answerValidation.getQuizAssignments().add(quizAssignment);
        quizAssignment.getAnswerValidations().add(answerValidation);

        answerValidationRepository.save(answerValidation);
        quizAssignmentRepository.save(quizAssignment);

        return modelMapper.map(quizAssignment, QuizAssignmentDTO.class);
    }

    @Override
    public QuizAssignmentDTO closeQuizAssignment(Long id) {
        double score = 0;
        QuizAssignment quizAssignment = quizAssignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz-assignment not found."));

        for (AnswerValidation answerValidation: quizAssignment.getAnswerValidations()) {
            for (QuizQuestion quizQuestion: quizAssignment.getQuiz().getQuizQuestions()) {
                // Check if the answer-validation is for selected question is matching the selected question in the quiz-question.
                if (Objects.equals(answerValidation.getQuestion().getId(), quizQuestion.getQuestion().getId())) {
                    if (quizQuestion.isAllowPartialPoints()) {
                        score += answerValidation.getPoints();
                    } else {
                        long correctSelectedAnswersCount = quizAssignment
                            .getAnswerValidations()
                            .stream().filter(av -> Objects
                                .equals(av.getQuestion().getId(), quizQuestion.getQuestion().getId())
                                && av.getPoints() > 0
                            ).count();

                        if (correctSelectedAnswersCount == answerValidation.getQuestion().getCorrectAnswersCount()) {
                            score += answerValidation.getPoints();
                        }
                    }
                }
            }
        }

        quizAssignment.setScore(score);

        quizAssignment = quizAssignmentRepository.save(quizAssignment);

        if (!quizAssignment.getQuiz().isCanSeeResult()) {
            quizAssignment.setScore(0);
            quizAssignment.setPassResult(0);
        }

        return modelMapper.map(quizAssignment, QuizAssignmentDTO.class);
    }
}

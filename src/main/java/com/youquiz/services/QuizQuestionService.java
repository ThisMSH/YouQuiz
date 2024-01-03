package com.youquiz.services;

import com.youquiz.dto.requestdto.QuizQuestionRequestDTO;
import com.youquiz.dto.responsedto.QuizQuestionDTO;
import com.youquiz.entities.AnswerValidation;
import com.youquiz.entities.Question;
import com.youquiz.entities.QuizQuestion;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import com.youquiz.exceptions.ResourceAlreadyExistsException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.exceptions.ResourceUnprocessableException;
import com.youquiz.repositories.QuestionRepository;
import com.youquiz.repositories.QuizQuestionRepository;
import com.youquiz.repositories.QuizRepository;
import com.youquiz.services.interfaces.IQuizQuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizQuestionService implements IQuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public QuizQuestionService(
        QuizQuestionRepository quizQuestionRepository,
        QuizRepository quizRepository,
        QuestionRepository questionRepository,
        ModelMapper modelMapper
    ) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QuizQuestionDTO create(QuizQuestionRequestDTO request) {
        QuizQuestionId key = new QuizQuestionId();
        key.setQuizId(request.getQuizId());
        key.setQuestionId(request.getQuestionId());

        Question question = questionRepository.findById(request.getQuestionId())
            .orElseThrow(() -> new ResourceNotFoundException("Question not found."));

        if (!quizRepository.existsById(request.getQuizId())) {
            throw new ResourceNotFoundException("Quiz not found.");
        }

        if (quizQuestionRepository.existsById(key)) {
            throw new ResourceAlreadyExistsException("The question is already assigned to this quiz.");
        }

        double points = question.getAnswerValidations().stream()
            .mapToDouble(AnswerValidation::getPoints).sum();

        if (points < question.getLevel().getMinPoints()) {
            throw new ResourceUnprocessableException("This question still didn't reach the minimum points required for its level.\nCurrent points for this question: " + points + "\nMinimum points required: " + question.getLevel().getMinPoints());
        }

        QuizQuestion qq = modelMapper.map(request, QuizQuestion.class);
        qq.setId(key);

        QuizQuestion createdQQ = quizQuestionRepository.save(qq);

        return modelMapper.map(createdQQ, QuizQuestionDTO.class);
    }

    @Override
    public QuizQuestionDTO update(QuizQuestionRequestDTO request) {
        return null;
    }

    @Override
    public QuizQuestionDTO delete(QuizQuestionId key) {
        QuizQuestion qq = quizQuestionRepository.findById(key)
            .orElseThrow(() -> new ResourceNotFoundException("Question/Quiz assignment not found."));

        quizQuestionRepository.deleteById(key);

        return modelMapper.map(qq, QuizQuestionDTO.class);
    }

    @Override
    public QuizQuestionDTO get(QuizQuestionId key) {
        QuizQuestion qq = quizQuestionRepository.findById(key)
            .orElseThrow(() -> new ResourceNotFoundException("Question/Quiz assignment not found."));

        return modelMapper.map(qq, QuizQuestionDTO.class);
    }

    @Override
    public Page<QuizQuestionDTO> getAll(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<QuizQuestionDTO> getAllByQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found.");
        }

        List<QuizQuestion> qqs = quizQuestionRepository.findAllByQuestionId(questionId);

        return qqs.stream().map(qq -> modelMapper.map(qq, QuizQuestionDTO.class)).toList();
    }

    @Override
    public List<QuizQuestionDTO> getAllByQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new ResourceNotFoundException("Quiz not found.");
        }

        List<QuizQuestion> qqs = quizQuestionRepository.findAllByQuestionId(quizId);

        return qqs.stream().map(qq -> modelMapper.map(qq, QuizQuestionDTO.class)).toList();
    }
}

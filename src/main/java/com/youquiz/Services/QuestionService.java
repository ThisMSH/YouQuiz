package com.youquiz.Services;

import com.youquiz.DTO.AltDTO.QuestionAltDTO;
import com.youquiz.DTO.MediaDTO;
import com.youquiz.DTO.NoParentAltDTO.AnswerValidationNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.LevelNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.MediaNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.SubjectNoParentDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Entities.Level;
import com.youquiz.Entities.Question;
import com.youquiz.Entities.Subject;
import com.youquiz.Enums.QuestionType;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.*;
import com.youquiz.Specifications.QuestionSpecification;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MediaService mediaService;
    private final ModelMapper modelMapper;
    private final LevelRepository levelRepository;
    private final SubjectRepository subjectRepository;
    private final AnswerValidationRepository avRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, MediaService mediaService, ModelMapper modelMapper, LevelRepository levelRepository, SubjectRepository subjectRepository, AnswerValidationRepository avRepository) {
        this.questionRepository = questionRepository;
        this.mediaService = mediaService;
        this.modelMapper = modelMapper;
        this.levelRepository = levelRepository;
        this.subjectRepository = subjectRepository;
        this.avRepository = avRepository;
    }

    public Question createQuestion(QuestionDTO q) {
        if (!levelRepository.existsById(q.getLevelId())) {
            throw new ResourceNotFoundException("The level does not exist.");
        }

        if (!subjectRepository.existsById(q.getSubjectId())) {
            throw new ResourceNotFoundException("The subject does not exist.");
        }

        Question question = modelMapper.map(q, Question.class);

        Question createdQuestion = questionRepository.save(question);

        if (q.getMediaDTOList() != null) {
            for (MediaDTO m : q.getMediaDTOList()) {
                m.setQuestionId(createdQuestion.getId());
                mediaService.createMedia(m);
            }
        }

        return questionRepository.findById(createdQuestion.getId()).get();
    }

    public QuestionAltDTO getQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found."));

        QuestionAltDTO q = modelMapper.map(question, QuestionAltDTO.class);

        LevelNoParentDTO lvlNoParent = q.getLevel();
        Level level = levelRepository.findById(question.getLevel().getId()).get();
        lvlNoParent.setQuestionIds(level.getQuestions() != null ? level.getQuestions().stream().map(Question::getId).collect(Collectors.toList()) : null);

        SubjectNoParentDTO subNoParent = q.getSubject();
        Subject subject = subjectRepository.findById(question.getSubject().getId()).get();
        subNoParent.setParentId(subject.getParent() != null ? subject.getParent().getId() : null);
        subNoParent.setChildrenIds(subject.getChildren() != null ? subject.getChildren().stream().map(Subject::getId).collect(Collectors.toList()) : null);
        subNoParent.setQuestionIds(subject.getQuestions() != null ? subject.getQuestions().stream().map(Question::getId).collect(Collectors.toList()) : null);

        List<MediaNoParentDTO> mediaNoParent = q.getMedias();
        mediaNoParent = mediaNoParent.stream()
            .peek(media -> media.setQuestionId(q.getId()))
            .collect(Collectors.toList());

        List<AnswerValidationNoParentDTO> avNoParent = q.getAnswerValidations();

        if (!avNoParent.isEmpty()) {
            List<AnswerValidation> av = avRepository.findByQuestionId(q.getId());

            for (int i = 0; i < avNoParent.size(); i++) {
                avNoParent.get(i).setQuestionId(av.get(i).getQuestion().getId());
                avNoParent.get(i).setAnswerId(av.get(i).getAnswer().getId());
            }

            q.setAnswerValidations(avNoParent);
        }

        q.setLevel(lvlNoParent);
        q.setSubject(subNoParent);
        q.setMedias(mediaNoParent);

        return q;
    }

    public Integer deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found.");
        }

        questionRepository.deleteById(id);

        return 1;
    }

    public Page<QuestionAltDTO> getAllQuestionsByFilters(String question, String type, Long levelId, Long subjectId, int page, int size, String sortBy, String sortOrder) {
        QuestionType questionType = null;
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        if (!type.isBlank()) {
            try {
                questionType = QuestionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Question type is not valid.");
            }
        }

        Page<Question> questions = questionRepository.findAll(
            Specification.where(QuestionSpecification.questionContains(question))
                .and(QuestionSpecification.typeEquals(questionType))
                .and(QuestionSpecification.getByLevel(levelId))
                .and(QuestionSpecification.getBySubject(subjectId)),
            pageable
        );

        Page<QuestionAltDTO> questionDTOs = questions.map(q -> modelMapper.map(q, QuestionAltDTO.class));

        if (!questions.hasContent()) {
            String message = "";
            if (questions.getTotalPages() > 0 && (page + 1) > questions.getTotalPages()) {
                message = "No questions found in the page " + (page + 1) + ".";
            } else {
                message = "No question was found.";
            }
            throw new ResourceNotFoundException(message);
        }

        return questionDTOs;
    }

    public Question updateQuestion(QuestionDTO q) {
        Optional<Question> questionOptional = questionRepository.findById(q.getId());

        if (questionOptional.isEmpty()) {
            throw new ResourceNotFoundException("The question does not exist.");
        }

        if (!levelRepository.existsById(q.getLevelId())) {
            throw new ResourceNotFoundException("The level does not exist, please select a valid level.");
        }

        if (!subjectRepository.existsById(q.getSubjectId())) {
            throw new ResourceNotFoundException("The subject does not exist, please select a valid subject.");
        }

        Level level = new Level();
        level.setId(q.getLevelId());

        Subject subject = new Subject();
        subject.setId(q.getSubjectId());

        Question question = questionOptional.get();
        question.setQuestion(q.getQuestion());
        question.setDescription(q.getDescription());
        question.setType(q.getType());
        question.setLevel(level);
        question.setSubject(subject);

        return questionRepository.save(question);
    }
}

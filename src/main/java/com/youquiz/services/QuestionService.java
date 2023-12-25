package com.youquiz.services;

import com.youquiz.dto.responsedto.AnswerValidationAltDTO;
import com.youquiz.dto.responsedto.QuestionAltDTO;
import com.youquiz.dto.requestdto.AnswerValidationNoParentDTO;
import com.youquiz.dto.requestdto.LevelNoParentDTO;
import com.youquiz.dto.requestdto.MediaNoParentDTO;
import com.youquiz.dto.requestdto.SubjectNoParentDTO;
import com.youquiz.dto.QuestionDTO;
import com.youquiz.entities.AnswerValidation;
import com.youquiz.entities.Level;
import com.youquiz.entities.Question;
import com.youquiz.entities.Subject;
import com.youquiz.enums.QuestionType;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.*;
import com.youquiz.specifications.QuestionSpecification;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        return questionRepository.save(question);

//        if (q.getMediaDTOList() != null) {
//            for (MediaDTO m : q.getMediaDTOList()) {
//                m.setQuestionId(createdQuestion.getId());
//                mediaService.createMedia(m);
//            }
//        }

//        return questionRepository.findById(createdQuestion.getId()).get();
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
            List<AnswerValidation> av = avRepository.findAllByQuestionId(q.getId());
            List<AnswerValidationAltDTO> avAltDTO = new ArrayList<>();

            for (int i = 0; i < avNoParent.size(); i++) {
                avNoParent.get(i).setQuestionId(av.get(i).getQuestion().getId());
                avNoParent.get(i).setAnswerId(av.get(i).getAnswer().getId());

                AnswerValidation answerValidation = avRepository.findById(av.get(i).getId()).orElseThrow(() -> new ResourceNotFoundException("The association between the answer and question was not found."));

                avAltDTO.add(modelMapper.map(
                    answerValidation,
                    AnswerValidationAltDTO.class
                ));
            }

            q.setAnswerValidations(avNoParent);
            q.setAnswerValidationsWithParent(avAltDTO);
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
        Question question = questionRepository.findById(q.getId()).orElseThrow(() -> new ResourceNotFoundException("The question does not exist."));

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

        question.setQuestion(q.getQuestion());
        question.setDescription(q.getDescription());
        question.setType(q.getType());
        question.setLevel(level);
        question.setSubject(subject);

        return questionRepository.save(question);
    }
}

package com.youquiz.services;

import com.youquiz.dto.requestdto.AnswerRequestDTO;
import com.youquiz.dto.requestdto.LevelRequestDTO;
import com.youquiz.dto.requestdto.QuestionRequestDTO;
import com.youquiz.dto.requestdto.SubjectRequestDTO;
import com.youquiz.dto.responsedto.AnswerValidationDTO;
import com.youquiz.dto.responsedto.MediaDTO;
import com.youquiz.dto.responsedto.QuestionDTO;
import com.youquiz.entities.*;
import com.youquiz.enums.QuestionType;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.AnswerValidationRepository;
import com.youquiz.repositories.LevelRepository;
import com.youquiz.repositories.QuestionRepository;
import com.youquiz.repositories.SubjectRepository;
import com.youquiz.services.interfaces.IQuestionService;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final LevelRepository levelRepository;
    private final SubjectRepository subjectRepository;
    private final AnswerValidationRepository avRepository;

    @Autowired
    public QuestionService(
        QuestionRepository questionRepository,
        ModelMapper modelMapper,
        LevelRepository levelRepository,
        SubjectRepository subjectRepository,
        AnswerValidationRepository avRepository
    ) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.levelRepository = levelRepository;
        this.subjectRepository = subjectRepository;
        this.avRepository = avRepository;
    }

    @Override
    public QuestionDTO create(QuestionRequestDTO request) {
        if (!levelRepository.existsById(request.getLevelId())) {
            throw new ResourceNotFoundException("Level not found.");
        }

        if (!subjectRepository.existsById(request.getSubjectId())) {
            throw new ResourceNotFoundException("Subject not found.");
        }

        Question question = questionRepository.save(modelMapper.map(request, Question.class));

        return modelMapper.map(question, QuestionDTO.class);
    }

    @Override
    public QuestionDTO update(QuestionRequestDTO request) {
        Question question = questionRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Question not found."));

        if (!levelRepository.existsById(request.getLevelId())) {
            throw new ResourceNotFoundException("Level not found.");
        }

        if (!subjectRepository.existsById(request.getSubjectId())) {
            throw new ResourceNotFoundException("Subject not found.");
        }

        Level level = new Level();
        level.setId(request.getLevelId());

        Subject subject = new Subject();
        subject.setId(request.getSubjectId());

        try {
            question.setType(QuestionType.valueOf(request.getType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Question type is not valid.");
        }

        question.setQuestion(request.getQuestion());
        question.setDescription(request.getDescription());
        question.setLevel(level);
        question.setSubject(subject);

        Question updatedQuestion = questionRepository.save(question);

        return modelMapper.map(updatedQuestion, QuestionDTO.class);
    }

    @Override
    public QuestionDTO delete(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found."));

        questionRepository.deleteById(id);

        return modelMapper.map(question, QuestionDTO.class);
    }

    @Override
    public QuestionDTO get(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found."));

        QuestionDTO q = modelMapper.map(question, QuestionDTO.class);

        LevelRequestDTO lvl = q.getLevel();
        Level level = levelRepository.findById(question.getLevel().getId()).orElse(null);
        lvl.setQuestionIds(level != null && level.getQuestions() != null ? level.getQuestions().stream().map(Question::getId).collect(Collectors.toList()) : null);

        SubjectRequestDTO subj = q.getSubject();
        Subject subject = subjectRepository.findById(question.getSubject().getId()).orElse(null);
        subj.setParentId(subject != null && subject.getParent() != null ? subject.getParent().getId() : null);
        subj.setChildrenIds(subject != null && subject.getChildren() != null ? subject.getChildren().stream().map(Subject::getId).collect(Collectors.toList()) : null);
        subj.setQuestionIds(subject != null && subject.getQuestions() != null ? subject.getQuestions().stream().map(Question::getId).collect(Collectors.toList()) : null);

        List<Media> media = question.getMedias();

        if (!media.isEmpty()) {
            List<MediaDTO> mediaDto = media.stream().map(m -> modelMapper.map(m, MediaDTO.class)).toList();

            q.setMedias(mediaDto);
        }

        List<AnswerValidation> av = avRepository.findAllByQuestionId(q.getId());

        if (!av.isEmpty()) {
            List<AnswerValidationDTO> avDto = new ArrayList<>();

            for (int i = 0; i < av.size(); i++) {
                avDto.add(modelMapper.map(av.get(i), AnswerValidationDTO.class));
                avDto.get(i).setQuestion(modelMapper.map(av.get(i).getQuestion(), QuestionRequestDTO.class));
                avDto.get(i).setAnswer(modelMapper.map(av.get(i).getAnswer(), AnswerRequestDTO.class));
            }

            q.setAnswerValidations(avDto);
        }

        q.setLevel(lvl);
        q.setSubject(subj);

        return q;
    }

    @Override
    public Page<QuestionDTO> getAll(Map<String, Object> params) {
        String question = (String) params.get("question");
        String type = (String) params.get("type");
        Long levelId = (Long) params.get("level");
        Long subjectId = (Long) params.get("subject");
        QuestionType questionType = null;
        Pageable pageable = Utilities.managePagination(params);

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

        return questions.map(q -> modelMapper.map(q, QuestionDTO.class));
    }
}

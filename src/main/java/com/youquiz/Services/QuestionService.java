package com.youquiz.Services;

import com.youquiz.DTO.AltDTO.QuestionAltDTO;
import com.youquiz.DTO.MediaDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Level;
import com.youquiz.Entities.Question;
import com.youquiz.Entities.Subject;
import com.youquiz.Enums.QuestionType;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.LevelRepository;
import com.youquiz.Repositories.QuestionRepository;
import com.youquiz.Repositories.SubjectRepository;
import com.youquiz.Specifications.QuestionSpecification;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MediaService mediaService;
    private final ModelMapper modelMapper;
    private final LevelRepository levelRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, MediaService mediaService, ModelMapper modelMapper, LevelRepository levelRepository, SubjectRepository subjectRepository) {
        this.questionRepository = questionRepository;
        this.mediaService = mediaService;
        this.modelMapper = modelMapper;
        this.levelRepository = levelRepository;
        this.subjectRepository = subjectRepository;
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

        return modelMapper.map(question, QuestionAltDTO.class);
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

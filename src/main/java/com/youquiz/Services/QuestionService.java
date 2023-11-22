package com.youquiz.Services;

import com.youquiz.DTO.AltDTO.QuestionAltDTO;
import com.youquiz.DTO.MediaDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Enums.QuestionType;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.QuestionRepository;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MediaService mediaService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, MediaService mediaService, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.mediaService = mediaService;
        this.modelMapper = modelMapper;
    }

    public Question createQuestion(QuestionDTO q) {
        q.setType(QuestionType.valueOf(q.getType().name().toUpperCase()));
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

    public Question getQuestion(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        return question.orElseThrow(() -> new ResourceNotFoundException("Question not found."));
    }

    public Integer deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found.");
        }

        questionRepository.deleteById(id);

        return 1;
    }

    public Page<QuestionAltDTO> getAllQuestionsByFilters(String question, String type, Long levelId, int page, int size, String sortBy, String sortOrder) {
        Page<Question> questions;
        QuestionType questionType;
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        try {
            questionType = QuestionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Question type is not valid.");
        }

        if (type.isBlank() && levelId == 0) {
            questions = questionRepository.findAllByQuestionLikeIgnoreCase("%" + question + "%", pageable);
        } else if (!type.isBlank() && levelId == 0) {
            questions = questionRepository.findAllByQuestionLikeIgnoreCaseAndType(
                "%" + question + "%",
                questionType,
                pageable
            );
        } else if (type.isBlank()) {
            questions = questionRepository.findAllByQuestionLikeIgnoreCaseAndLevelId(
                "%" + question + "%",
                levelId,
                pageable
            );
        } else {
            questions = questionRepository.findAllByQuestionLikeIgnoreCaseAndTypeAndLevelId(
                "%" + question + "%",
                questionType,
                levelId,
                pageable
            );
        }

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

    public Question updateQuestion(Question question) {
        return null;
    }
}

package com.youquiz.Services;

import com.youquiz.DTO.Alt.QuestionAltDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.ResourceBadRequest;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.QuestionRepository;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    public Question createQuestion(QuestionDTO q) {
        Question question = modelMapper.map(q, Question.class);

        return questionRepository.save(question);
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

    public Page<QuestionAltDTO> getAllQuestions(String question, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        Page<Question> questions = questionRepository.findAllByQuestionLikeIgnoreCase("%" + question + "%", pageable);

        Page<QuestionAltDTO> questionDTOs = questions.map(q -> modelMapper.map(q, QuestionAltDTO.class));

        if (!questions.hasContent()) {
            String message = "";
            if (question.isBlank()) {
                message = "No questions found in page " + (page + 1) + ".";
            } else {
                message = "No question matching \"" + question + "\" was found.";
            }
            throw new ResourceNotFoundException(message);
        }

        return questionDTOs;
    }
}

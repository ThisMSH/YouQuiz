package com.youquiz.Services;

import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}

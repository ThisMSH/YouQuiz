package com.youquiz.Services;

import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Repositories.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    public String createQuestion(QuestionDTO q) {
        // Add validation later
        Question question = modelMapper.map(q, Question.class);
        questionRepository.save(question);
        return "Question has been created successfully.";
    }

    public Question getQuestion(Long id) {
        // Add better management for this method later
        return questionRepository.findById(id).orElseThrow();
    }
}

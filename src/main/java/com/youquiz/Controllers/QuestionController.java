package com.youquiz.Controllers;

import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/add")
    public String createQuestion(@RequestBody QuestionDTO question) {
        return questionService.createQuestion(question);
    }
}

package com.youquiz.Controllers;

import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Services.QuestionService;
import com.youquiz.Utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuestion(@PathVariable Long id) {
        Question question = questionService.getQuestion(id);

        return ResponseHandler.success(
            "The question has been fetched successfully.",
            HttpStatus.OK,
            question
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createQuestion(@RequestBody QuestionDTO question) {
        Question createdQuestion = questionService.createQuestion(question);

        return ResponseHandler.success(
            "The question has been created successfully.",
            HttpStatus.CREATED,
            createdQuestion
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        int del = questionService.deleteQuestion(id);

        return ResponseHandler.success(
            "The question has been deleted successfully.",
            HttpStatus.OK,
            del
        );
    }
}

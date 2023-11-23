package com.youquiz.Controllers;

import com.youquiz.DTO.AltDTO.QuestionAltDTO;
import com.youquiz.DTO.QuestionDTO;
import com.youquiz.Entities.Question;
import com.youquiz.Services.QuestionService;
import com.youquiz.Utils.ResponseHandler;
import jakarta.validation.Valid;
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
        QuestionAltDTO question = questionService.getQuestion(id);

        return ResponseHandler.success(
            "The question has been fetched successfully.",
            HttpStatus.OK,
            question
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllQuestions(
        @RequestParam(defaultValue = "") String question,
        @RequestParam(defaultValue = "") String type,
        @RequestParam(defaultValue = "0") Long level,
        @RequestParam(defaultValue = "0") Long subject,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        var questions = questionService.getAllQuestionsByFilters(question, type, level, subject, page - 1, size, sortBy, sortOrder);

        return ResponseHandler.success(
            "The questions have been fetched successfully.",
            HttpStatus.OK,
            questions
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createQuestion(@ModelAttribute @Valid QuestionDTO question) {
        Question createdQuestion = questionService.createQuestion(question);

        return ResponseHandler.success(
            "The question has been created successfully.",
            HttpStatus.CREATED,
            createdQuestion
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateQuestion(@RequestBody @Valid QuestionDTO question) {
        Question updatedQuestion = questionService.updateQuestion(question);

        return ResponseHandler.success(
            "The question has been updated successfully.",
            HttpStatus.OK,
            updatedQuestion
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

package com.youquiz.controllers;

import com.youquiz.dto.requestdto.QuestionRequestDTO;
import com.youquiz.dto.responsedto.QuestionDTO;
import com.youquiz.services.QuestionService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        QuestionDTO question = questionService.get(id);

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
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("question", question);
        params.put("type", type);
        params.put("level", level);
        params.put("subject", subject);

        var questions = questionService.getAll(params);

        return ResponseHandler.success(
            "The questions have been fetched successfully.",
            HttpStatus.OK,
            questions
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createQuestion(@RequestBody @Valid QuestionRequestDTO question) {
        QuestionDTO createdQuestion = questionService.create(question);

        return ResponseHandler.success(
            "The question has been created successfully.",
            HttpStatus.CREATED,
            createdQuestion
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateQuestion(@RequestBody @Valid QuestionRequestDTO question) {
        QuestionDTO updatedQuestion = questionService.update(question);

        return ResponseHandler.success(
            "The question has been updated successfully.",
            HttpStatus.OK,
            updatedQuestion
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        QuestionDTO deletedQuestion = questionService.delete(id);

        return ResponseHandler.success(
            "The question has been deleted successfully.",
            HttpStatus.OK,
            deletedQuestion
        );
    }
}

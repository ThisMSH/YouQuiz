package com.youquiz.controllers;

import com.youquiz.dto.requestdto.QuizRequestDTO;
import com.youquiz.services.QuizService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuiz(@PathVariable Long id) {
        var quiz = quizService.get(id);

        return ResponseHandler.success(
            "The quiz has been fetched successfully.",
            HttpStatus.OK,
            quiz
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllQuizzes(
        @RequestParam(defaultValue = "") String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("title", title);
        var quizzes = quizService.getAll(params);

        return ResponseHandler.success(
            "The quizzes have been fetched successfully.",
            HttpStatus.OK,
            quizzes
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createQuiz(@RequestBody @Valid QuizRequestDTO q) {
        var quiz = quizService.create(q);

        return ResponseHandler.success(
            "The quiz has been created successfully.",
            HttpStatus.CREATED,
            quiz
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateQuiz(@RequestBody @Valid QuizRequestDTO q) {
        var quiz = quizService.update(q);

        return ResponseHandler.success(
            "The quiz has been updated successfully.",
            HttpStatus.OK,
            quiz
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id) {
        var quiz = quizService.delete(id);

        return ResponseHandler.success(
            "The quiz has been deleted successfully.",
            HttpStatus.OK,
            quiz
        );
    }
}

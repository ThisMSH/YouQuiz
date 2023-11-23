package com.youquiz.Controllers;

import com.youquiz.Entities.Answer;
import com.youquiz.Services.AnswerService;
import com.youquiz.Utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnswer(@PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);

        return ResponseHandler.success(
            "The answer has been fetched successfully.",
            HttpStatus.OK,
            answer
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllAnswers(
        @RequestParam(defaultValue = "") String text,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        var answers = answerService.getAllAnswers(text, page - 1, size, sortBy, sortOrder);

        return ResponseHandler.success(
            "The answers have been fetched successfully.",
            HttpStatus.OK,
            answers
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createAnswer(@RequestBody @Valid Answer answer) {
        Answer createdAnswer = answerService.createAnswer(answer);

        return ResponseHandler.success(
            "The answer has been created successfully.",
            HttpStatus.CREATED,
            createdAnswer
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAnswer(@RequestBody @Valid Answer answer) {
        Answer updatedAnswer = answerService.updateAnswer(answer);

        return ResponseHandler.success(
            "The answer has been updated successfully.",
            HttpStatus.OK,
            updatedAnswer
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnswer(@PathVariable("id") Long id) {
        int del = answerService.deleteAnswer(id);

        return ResponseHandler.success(
            "The answer has been deleted successfully.",
            HttpStatus.OK,
            del
        );
    }
}

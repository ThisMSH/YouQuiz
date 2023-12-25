package com.youquiz.controllers;

import com.youquiz.dto.AnswerValidationDTO;
import com.youquiz.entities.AnswerValidation;
import com.youquiz.services.AnswerValidationService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/answers-assignment")
public class AnswerValidationController {
    private final AnswerValidationService avService;

    @Autowired
    public AnswerValidationController(AnswerValidationService avService) {
        this.avService = avService;
    }

    @GetMapping("/{questionId}-{answerId}")
    public ResponseEntity<Object> getAssignedAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        AnswerValidation av = avService.getAssignedAnswer(questionId, answerId);

        return ResponseHandler.success(
            "Answer successfully assigned to the question.",
            HttpStatus.OK,
            av
        );
    }

    @GetMapping("/by-question-{questionId}")
    public ResponseEntity<Object> getAssignedAnswersByQuestion(@PathVariable Long questionId) {
        var avList = avService.getAssignedAnswersByQuestion(questionId);

        return ResponseHandler.success(
            "Assigned answers have been fetched by question ID successfully.",
            HttpStatus.OK,
            avList
        );
    }

    @GetMapping("/by-answer-{answerId}")
    public ResponseEntity<Object> getAssignedAnswersByAnswer(@PathVariable Long answerId) {
        var avList = avService.getAssignedAnswersByAnswer(answerId);

        return ResponseHandler.success(
            "Assigned answers have been fetched by question ID successfully.",
            HttpStatus.OK,
            avList
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> assignAnswerToQuestion(@RequestBody @Valid AnswerValidationDTO a) {
        AnswerValidation av = avService.assignAnswerToQuestion(a);

        return ResponseHandler.success(
            "Successfully retrieved the association between the answer and question.",
            HttpStatus.CREATED,
            av
        );
    }

    @DeleteMapping("/{questionId}-{answerId}")
    public ResponseEntity<Object> deleteAnswerValidation(@PathVariable Long questionId, @PathVariable Long answerId) {
        int del = avService.deleteAnswerValidation(questionId, answerId);

        return ResponseHandler.success(
            "Successfully removed the association between the answer and question.",
            HttpStatus.OK,
            del
        );
    }
}

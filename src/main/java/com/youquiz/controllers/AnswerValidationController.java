package com.youquiz.controllers;

import com.youquiz.dto.requestdto.AnswerValidationRequestDTO;
import com.youquiz.dto.responsedto.AnswerValidationDTO;
import com.youquiz.services.AnswerValidationService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers-assignment")
public class AnswerValidationController {
    private final AnswerValidationService avService;

    @Autowired
    public AnswerValidationController(AnswerValidationService avService) {
        this.avService = avService;
    }

    @GetMapping("/pk/{id}")
    public ResponseEntity<Object> getAssignedAnswerByPK(@PathVariable Long id) {
        AnswerValidationDTO av = avService.get(id);

        return ResponseHandler.success(
            "Answer successfully assigned to the question.",
            HttpStatus.OK,
            av
        );
    }

    @GetMapping("/fk/{questionId}-{answerId}")
    public ResponseEntity<Object> getAssignedAnswerByFK(@PathVariable Long questionId, @PathVariable Long answerId) {
        AnswerValidationDTO av = avService.get(questionId, answerId);

        return ResponseHandler.success(
            "Answer successfully assigned to the question.",
            HttpStatus.OK,
            av
        );
    }

    @GetMapping("/by-question/{questionId}")
    public ResponseEntity<Object> getAssignedAnswersByQuestion(@PathVariable Long questionId) {
        var avList = avService.getAssignedAnswersByQuestion(questionId);

        return ResponseHandler.success(
            "Assigned answers have been fetched by question ID successfully.",
            HttpStatus.OK,
            avList
        );
    }

    @GetMapping("/by-answer/{answerId}")
    public ResponseEntity<Object> getAssignedAnswersByAnswer(@PathVariable Long answerId) {
        var avList = avService.getAssignedAnswersByAnswer(answerId);

        return ResponseHandler.success(
            "Assigned answers have been fetched by question ID successfully.",
            HttpStatus.OK,
            avList
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> assignAnswerToQuestion(@RequestBody @Valid AnswerValidationRequestDTO a) {
        AnswerValidationDTO av = avService.create(a);

        return ResponseHandler.success(
            "Successfully retrieved the association between the answer and question.",
            HttpStatus.CREATED,
            av
        );
    }

    @DeleteMapping("/pk/{id}")
    public ResponseEntity<Object> deleteAnswerValidation(@PathVariable Long id) {
        AnswerValidationDTO av = avService.delete(id);

        return ResponseHandler.success(
            "Successfully removed the association between the answer and question.",
            HttpStatus.OK,
            av
        );
    }

    @DeleteMapping("/fk/{questionId}-{answerId}")
    public ResponseEntity<Object> deleteAnswerValidation(@PathVariable Long questionId, @PathVariable Long answerId) {
        AnswerValidationDTO av = avService.delete(questionId, answerId);

        return ResponseHandler.success(
            "Successfully removed the association between the answer and question.",
            HttpStatus.OK,
            av
        );
    }
}

package com.youquiz.controllers;

import com.youquiz.dto.requestdto.AnswerRequestDTO;
import com.youquiz.dto.responsedto.AnswerDTO;
import com.youquiz.services.AnswerService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        AnswerDTO answer = answerService.get(id);

        return ResponseHandler.success(
            "The answer has been fetched successfully.",
            HttpStatus.OK,
            answer
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllAnswers(
        @RequestParam(defaultValue = "") String text,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("text", text);
        var answers = answerService.getAll(params);

        return ResponseHandler.success(
            "The answers have been fetched successfully.",
            HttpStatus.OK,
            answers
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createAnswer(@RequestBody @Valid AnswerRequestDTO answer) {
        AnswerDTO createdAnswer = answerService.create(answer);

        return ResponseHandler.success(
            "The answer has been created successfully.",
            HttpStatus.CREATED,
            createdAnswer
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAnswer(@RequestBody @Valid AnswerRequestDTO answer) {
        AnswerDTO updatedAnswer = answerService.update(answer);

        return ResponseHandler.success(
            "The answer has been updated successfully.",
            HttpStatus.OK,
            updatedAnswer
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnswer(@PathVariable("id") Long id) {
        AnswerDTO deletedAnswer = answerService.delete(id);

        return ResponseHandler.success(
            "The answer has been deleted successfully.",
            HttpStatus.OK,
            deletedAnswer
        );
    }
}

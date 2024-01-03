package com.youquiz.controllers;

import com.youquiz.dto.requestdto.QuizAssignmentRequestDTO;
import com.youquiz.services.QuizAssignmentService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz-assignments")
public class QuizAssignmentController {
    private final QuizAssignmentService quizAssignmentService;

    @Autowired
    public QuizAssignmentController(QuizAssignmentService quizAssignmentService) {
        this.quizAssignmentService = quizAssignmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuizAssignment(@PathVariable("id") Long id) {
        var quizAssignment = quizAssignmentService.get(id);

        return ResponseHandler.success(
            "The quiz-assignment has been fetched successfully.",
            HttpStatus.OK,
            quizAssignment
        );
    }

    @GetMapping("/with-results/{id}")
    public ResponseEntity<Object> getQuizAssignmentWithResults(@PathVariable("id") Long id) {
        var quizAssignment = quizAssignmentService.getWithResults(id);

        return ResponseHandler.success(
            "The quiz-assignment has been fetched successfully.",
            HttpStatus.OK,
            quizAssignment
        );
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<Object> getQuizAssignmentsByStudent(@PathVariable("studentId") Long studentId) {
        var quizAssignments = quizAssignmentService.getByStudent(studentId);

        return ResponseHandler.success(
            "The quiz-assignments have been fetched successfully.",
            HttpStatus.OK,
            quizAssignments
        );
    }

    @GetMapping("/by-student-with-results/{studentId}")
    public ResponseEntity<Object> getQuizAssignmentsByStudentWithResults(@PathVariable("studentId") Long studentId) {
        var quizAssignments = quizAssignmentService.getByStudentWithResults(studentId);

        return ResponseHandler.success(
            "The quiz-assignments have been fetched successfully.",
            HttpStatus.OK,
            quizAssignments
        );
    }

    @PostMapping("/close-quiz-assignment/{id}")
    public ResponseEntity<Object> closeQuizAssignment(@PathVariable("id") Long id) {
        var quizAssignment = quizAssignmentService.closeQuizAssignment(id);

        return ResponseHandler.success(
            "The quiz-assignment has been closed successfully.",
            HttpStatus.OK,
            quizAssignment
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createQuizAssignment(@RequestBody @Valid QuizAssignmentRequestDTO quizAssignment) {
        var createdQuizAssignment = quizAssignmentService.create(quizAssignment);

        return ResponseHandler.success(
            "The quiz-assignment has been created successfully.",
            HttpStatus.CREATED,
            createdQuizAssignment
        );
    }

    @PostMapping("/selected-answer/{quizAssignmentId}-{answerValidationId}")
    public ResponseEntity<Object> saveSelectedAnswer(
        @PathVariable("answerValidationId") Long answerValidationId,
        @PathVariable("quizAssignmentId") Long quizAssignmentId
    ) {
        var quizAssignment = quizAssignmentService.saveSelectedAnswer(answerValidationId, quizAssignmentId);

        return ResponseHandler.success(
            "The selected answer has been saved successfully.",
            HttpStatus.OK,
            quizAssignment
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuizAssignment(@PathVariable("id") Long id) {
        var deletedQuizAssignment = quizAssignmentService.delete(id);

        return ResponseHandler.success(
            "The quiz-assignment has been deleted successfully.",
            HttpStatus.OK,
            deletedQuizAssignment
        );
    }

}

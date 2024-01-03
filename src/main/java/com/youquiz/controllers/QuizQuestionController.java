package com.youquiz.controllers;

import com.youquiz.dto.requestdto.QuizQuestionRequestDTO;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import com.youquiz.services.QuizQuestionService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz-questions")
public class QuizQuestionController {
    private final QuizQuestionService quizQuestionService;

    @Autowired
    public QuizQuestionController(QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping("/{quizId}/{questionId}")
    public ResponseEntity<Object> getQuizQuestion(@PathVariable Long quizId, @PathVariable Long questionId) {
        QuizQuestionId key = new QuizQuestionId();
        key.setQuizId(quizId);
        key.setQuestionId(questionId);

        var quizQuestion = quizQuestionService.get(key);

        return ResponseHandler.success(
            "The quiz-question has been fetched successfully.",
            HttpStatus.OK,
            quizQuestion
        );
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<Object> getAllByQuiz(@PathVariable Long quizId) {
        var quizQuestions = quizQuestionService.getAllByQuiz(quizId);

        return ResponseHandler.success(
            "The quiz-questions by quiz have been fetched successfully.",
            HttpStatus.OK,
            quizQuestions
        );
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<Object> getAllByQuestion(@PathVariable Long questionId) {
        var quizQuestions = quizQuestionService.getAllByQuestion(questionId);

        return ResponseHandler.success(
            "The quiz-questions by question have been fetched successfully.",
            HttpStatus.OK,
            quizQuestions
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createQuizQuestion(@RequestBody @Valid QuizQuestionRequestDTO qq) {
        var quizQuestion = quizQuestionService.create(qq);

        return ResponseHandler.success(
            "The quiz-question has been created successfully.",
            HttpStatus.CREATED,
            quizQuestion
        );
    }

    @DeleteMapping("/{quizId}/{questionId}")
    public ResponseEntity<Object> deleteQuizQuestion(@PathVariable Long quizId, @PathVariable Long questionId) {
        QuizQuestionId key = new QuizQuestionId();
        key.setQuizId(quizId);
        key.setQuestionId(questionId);

        var quizQuestion = quizQuestionService.delete(key);

        return ResponseHandler.success(
            "The quiz-question has been deleted successfully.",
            HttpStatus.OK,
            quizQuestion
        );
    }
}

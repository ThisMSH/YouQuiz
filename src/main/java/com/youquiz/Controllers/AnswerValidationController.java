package com.youquiz.Controllers;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Services.AnswerValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers-assignment")
public class AnswerValidationController {
    private final AnswerValidationService avService;

    @Autowired
    public AnswerValidationController(AnswerValidationService avService) {
        this.avService = avService;
    }

    @GetMapping("/{questionId}-{answerId}")
    public AnswerValidation getAssignedAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        return avService.getAssignedAnswer(questionId, answerId);
    }

    @PostMapping("/add")
    public String assignAnswerToQuestion(@RequestBody AnswerValidationDTO a) {
        return avService.assignAnswerToQuestion(a);
    }
}

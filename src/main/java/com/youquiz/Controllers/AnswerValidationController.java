package com.youquiz.Controllers;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Services.AnswerValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers-assignment")
public class AnswerValidationController {
    private final AnswerValidationService avService;

    @Autowired
    public AnswerValidationController(AnswerValidationService avService) {
        this.avService = avService;
    }

    @PostMapping("/add")
    public String assignAnswerToQuestion(@RequestBody AnswerValidationDTO a) {
        return avService.assignAnswerToQuestion(a);
    }
}

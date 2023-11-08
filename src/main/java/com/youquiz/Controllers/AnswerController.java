package com.youquiz.Controllers;

import com.youquiz.Entities.Answer;
import com.youquiz.Services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Answer getAnswer(@PathVariable("id") Long id) {
        return answerService.getAnswer(id);
    }

    @PostMapping("/add")
    public String createAnswer(@RequestBody Answer answer) {
        return answerService.createAnswer(answer);
    }
}

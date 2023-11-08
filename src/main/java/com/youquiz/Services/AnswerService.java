package com.youquiz.Services;

import com.youquiz.Entities.Answer;
import com.youquiz.Repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public String createAnswer(Answer answer) {
        // Add validation later
        answerRepository.save(answer);
        return "The answer \"" + answer.getAnswer() + "\" has been created successfully.";
    }
}

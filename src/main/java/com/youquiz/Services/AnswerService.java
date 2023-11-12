package com.youquiz.Services;

import com.youquiz.Entities.Answer;
import com.youquiz.Repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Answer getAnswer(Long id) {
        // Add better management for this method
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.orElseThrow();
    }

    public String deleteAnswer(Long id) {
        // Check if the answer exist
        answerRepository.deleteById(id);
        return "The answer has been deleted successfully.";
    }
}

package com.youquiz.Services;

import com.youquiz.Entities.Answer;
import com.youquiz.Exceptions.ResourceAlreadyExists;
import com.youquiz.Exceptions.ResourceNotFoundException;
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

    public Answer createAnswer(Answer answer) {
        if (answerRepository.existsByAnswerIgnoreCase(answer.getAnswer())) {
            throw new ResourceAlreadyExists("This answer already exists.");
        }

        return answerRepository.save(answer);
    }

    public Answer getAnswer(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);

        return answer.orElseThrow(() -> new ResourceNotFoundException("Answer not found."));
    }

    public Integer deleteAnswer(Long id) {
        if (!answerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Answer not found.");
        }

        answerRepository.deleteById(id);

        return 1;
    }
}

package com.youquiz.Services;

import com.youquiz.DTO.AltDTO.AnswerAltDTO;
import com.youquiz.Entities.Answer;
import com.youquiz.Exceptions.ResourceAlreadyExists;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.AnswerRepository;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
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

    public Page<AnswerAltDTO> getAllAnswers(String text, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        Page<Answer> answers = answerRepository.findAllByAnswerLikeIgnoreCase("%" + text + "%", pageable);

        Page<AnswerAltDTO> answerDTOs = answers.map(an -> modelMapper.map(an, AnswerAltDTO.class));

        if (!answers.hasContent()) {
            String message = "";
            if (text.isBlank()) {
                message = "No answers found in page " + (page + 1) + ".";
            } else {
                message = "No answer matching \"" + text + "\" was found.";
            }
            throw new ResourceNotFoundException(message);
        }

        return answerDTOs;
    }
}

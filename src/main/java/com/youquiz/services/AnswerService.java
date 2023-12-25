package com.youquiz.services;

import com.youquiz.dto.responsedto.AnswerDTO;
import com.youquiz.entities.Answer;
import com.youquiz.exceptions.ResourceAlreadyExistsException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.AnswerRepository;
import com.youquiz.utils.Utilities;
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
            throw new ResourceAlreadyExistsException("This answer already exists.");
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

    public Page<AnswerDTO> getAllAnswers(String text, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        Page<Answer> answers = answerRepository.findAllByAnswerLikeIgnoreCase("%" + text + "%", pageable);

        Page<AnswerDTO> answerDTOs = answers.map(an -> modelMapper.map(an, AnswerDTO.class));

        if (!answers.hasContent()) {
            String message = "";
            if (answers.getTotalPages() > 0 && (page + 1) > answers.getTotalPages()) {
                message = "No answers found in page " + (page + 1) + ".";
            } else {
                message = "No answer was found.";
            }
            throw new ResourceNotFoundException(message);
        }

        return answerDTOs;
    }

    public Answer updateAnswer(Answer a) {
        Answer answer = answerRepository.findById(a.getId()).orElseThrow(() -> new ResourceNotFoundException("The answer does not found"));

        answer.setAnswer(a.getAnswer());

        return answerRepository.save(answer);
    }
}

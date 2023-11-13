package com.youquiz.Services;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Repositories.AnswerValidationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerValidationService {
    private final AnswerValidationRepository avRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswerValidationService(AnswerValidationRepository avRepository, ModelMapper modelMapper) {
        this.avRepository = avRepository;
        this.modelMapper = modelMapper;
    }

    public String assignAnswerToQuestion(AnswerValidationDTO a) {
        // Check if an answer is already assigned to a question
        AnswerValidation answer = modelMapper.map(a, AnswerValidation.class);
        avRepository.save(answer);
        return "Answer has been assigned successfully.";
    }

    public AnswerValidation getAssignedAnswer(Long questionId, Long answerId) {
        return avRepository.findByQuestionIdAndAnswerId(questionId, answerId);
    }

    @Transactional
    public String deleteAnswerValidation(Long questionId, Long answerId) {
        // Check if there is an assignment
        avRepository.deleteByQuestionIdAndAnswerId(questionId, answerId);
        return "Answer/Question assignment has been deleted successfully.";
    }
}

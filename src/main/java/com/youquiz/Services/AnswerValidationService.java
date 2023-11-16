package com.youquiz.Services;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Exceptions.ResourceNotFoundException;
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

    public AnswerValidation assignAnswerToQuestion(AnswerValidationDTO a) {
        if (avRepository.existsByQuestionIdAndAnswerId(a.getQuestionId(), a.getAnswerId())) {
            throw new ResourceNotFoundException("The answer is already assigned to this question.");
        }

        AnswerValidation answer = modelMapper.map(a, AnswerValidation.class);

        return avRepository.save(answer);
    }

    public AnswerValidation getAssignedAnswer(Long questionId, Long answerId) {
        Optional<AnswerValidation> av = avRepository.findByQuestionIdAndAnswerId(questionId, answerId);

        return av.orElseThrow(() -> new ResourceNotFoundException("The association between the answer and question was not found."));
    }

    @Transactional
    public Integer deleteAnswerValidation(Long questionId, Long answerId) {
        if (!avRepository.existsByQuestionIdAndAnswerId(questionId, answerId)) {
            throw new ResourceNotFoundException("No association found between the provided answer and question.");
        }

        avRepository.deleteByQuestionIdAndAnswerId(questionId, answerId);

        return 1;
    }
}

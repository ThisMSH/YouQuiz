package com.youquiz.Services;

import com.youquiz.DTO.AnswerValidationDTO;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Repositories.AnswerValidationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return modelMapper.map(a, AnswerValidation.class);
    }
}

package com.youquiz.services;

import com.youquiz.dto.responsedto.AnswerValidationDTO;
import com.youquiz.entities.AnswerValidation;
import com.youquiz.enums.QuestionType;
import com.youquiz.exceptions.ResourceBadRequestException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.AnswerRepository;
import com.youquiz.repositories.AnswerValidationRepository;
import com.youquiz.repositories.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerValidationService {
    private final AnswerValidationRepository avRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswerValidationService(AnswerValidationRepository avRepository, AnswerRepository answerRepository, QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.avRepository = avRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    public AnswerValidation assignAnswerToQuestion(com.youquiz.dto.AnswerValidationDTO a) {
        if (!questionRepository.existsById(a.getQuestionId())) {
            throw new ResourceNotFoundException("The question does not exist.");
        }

        if (!answerRepository.existsById(a.getAnswerId())) {
            throw new ResourceNotFoundException("The answer does not exist.");
        }

        if (avRepository.existsByQuestionIdAndAnswerId(a.getQuestionId(), a.getAnswerId())) {
            throw new ResourceNotFoundException("The answer is already assigned to this question.");
        }

        List<AnswerValidation> avList = avRepository.findAllByQuestionId(a.getQuestionId());

        if (!avList.isEmpty() && avList.get(0).getQuestion().getType() == QuestionType.SINGLE) {
            for (AnswerValidation av : avList) {
                if (av.getPoints() > 0) {
                    throw new ResourceBadRequestException("Questions of type \"Single\" don't accept more than one correct answer.");
                }
            }
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

    public List<AnswerValidationDTO> getAssignedAnswersByQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("The question does not exist.");
        }

        return avRepository.findAllByQuestionId(questionId).stream()
            .map(av -> modelMapper.map(av, AnswerValidationDTO.class))
            .collect(Collectors.toList());
    }

    public List<AnswerValidationDTO> getAssignedAnswersByAnswer(Long answerId) {
        if (!answerRepository.existsById(answerId)) {
            throw new ResourceNotFoundException("The answer does not exist.");
        }

        return avRepository.findAllByAnswerId(answerId).stream()
            .map(av -> modelMapper.map(av, AnswerValidationDTO.class))
            .collect(Collectors.toList());
    }
}

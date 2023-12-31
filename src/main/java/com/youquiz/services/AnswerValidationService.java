package com.youquiz.services;

import com.youquiz.dto.requestdto.AnswerValidationRequestDTO;
import com.youquiz.dto.responsedto.AnswerValidationDTO;
import com.youquiz.entities.AnswerValidation;
import com.youquiz.entities.Question;
import com.youquiz.enums.QuestionType;
import com.youquiz.exceptions.ResourceBadRequestException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.exceptions.ResourceUnprocessableException;
import com.youquiz.repositories.AnswerRepository;
import com.youquiz.repositories.AnswerValidationRepository;
import com.youquiz.repositories.QuestionRepository;
import com.youquiz.services.interfaces.IAnswerValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswerValidationService implements IAnswerValidationService {
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

    @Override
    public AnswerValidationDTO create(AnswerValidationRequestDTO request) {
        Question question = questionRepository.findById(request.getQuestionId())
            .orElseThrow(() -> new ResourceNotFoundException("The question does not exist."));

        if (!answerRepository.existsById(request.getAnswerId())) {
            throw new ResourceNotFoundException("The answer does not exist.");
        }

        if (avRepository.existsByQuestionIdAndAnswerId(request.getQuestionId(), request.getAnswerId())) {
            throw new ResourceNotFoundException("The answer is already assigned to this question.");
        }

        List<AnswerValidation> avList = avRepository.findAllByQuestionId(request.getQuestionId());

        if (!avList.isEmpty() && avList.get(0).getQuestion().getType() == QuestionType.SINGLE) {
            for (AnswerValidation av : avList) {
                if (av.getPoints() > 0 && request.getPoints() > 0) {
                    throw new ResourceBadRequestException("Questions of type \"Single\" don't accept more than one correct answer.");
                }
            }
        }

        double points = question.getAnswerValidations().stream()
            .mapToDouble(AnswerValidation::getPoints).sum();

        if (request.getPoints() > 0) {
            if (points == question.getLevel().getMaxPoints()) {
                throw new ResourceUnprocessableException("The maximum number of points for this question has already been reached.");
            } else if (points + request.getPoints() > question.getLevel().getMaxPoints()) {
                throw new ResourceUnprocessableException(
                    "The maximum number of points for this question will be exceeded." +
                        "\nMax points for this question: " + question.getLevel().getMaxPoints() +
                        "\nCurrent points for this question: " + points +
                        "\nPoints to be added: " + request.getPoints() +
                        "\nNew total points for this question: " + (points + request.getPoints())
                );
            }
        }

        AnswerValidation av = modelMapper.map(request, AnswerValidation.class);

        return modelMapper.map(avRepository.save(av), AnswerValidationDTO.class);
    }

    @Override
    public AnswerValidationDTO delete(Long id) {
        AnswerValidation av = avRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Association not found between the provided answer and question."));

        avRepository.deleteById(id);

        return modelMapper.map(av, AnswerValidationDTO.class);
    }

    @Override
    @Transactional
    public AnswerValidationDTO delete(Long questionId, Long answerId) {
        AnswerValidation av = avRepository.findByQuestionIdAndAnswerId(questionId, answerId)
            .orElseThrow(() -> new ResourceNotFoundException("Association not found between the provided answer and question."));

        avRepository.deleteByQuestionIdAndAnswerId(questionId, answerId);

        return modelMapper.map(av, AnswerValidationDTO.class);
    }

    @Override
    public AnswerValidationDTO get(Long id) {
        AnswerValidation av = avRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("The association between the answer and question was not found."));

        return modelMapper.map(av, AnswerValidationDTO.class);
    }

    @Override
    public AnswerValidationDTO get(Long questionId, Long answerId) {
        AnswerValidation av = avRepository.findByQuestionIdAndAnswerId(questionId, answerId)
            .orElseThrow(() -> new ResourceNotFoundException("The association between the answer and question was not found."));

        return modelMapper.map(av, AnswerValidationDTO.class);
    }

    @Override
    public List<AnswerValidationDTO> getAssignedAnswersByQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("The question does not exist.");
        }

        return avRepository.findAllByQuestionId(questionId).stream()
            .map(av -> modelMapper.map(av, AnswerValidationDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<AnswerValidationDTO> getAssignedAnswersByAnswer(Long answerId) {
        if (!answerRepository.existsById(answerId)) {
            throw new ResourceNotFoundException("The answer does not exist.");
        }

        return avRepository.findAllByAnswerId(answerId).stream()
            .map(av -> modelMapper.map(av, AnswerValidationDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public AnswerValidationDTO update(AnswerValidationRequestDTO request) {
        return null;
    }

    @Override
    public Page<AnswerValidationDTO> getAll(Map<String, Object> params) {
        return null;
    }
}

package com.youquiz.services;

import com.youquiz.dto.requestdto.AnswerRequestDTO;
import com.youquiz.dto.responsedto.AnswerDTO;
import com.youquiz.dto.responsedto.AnswerValidationDTO;
import com.youquiz.entities.Answer;
import com.youquiz.entities.AnswerValidation;
import com.youquiz.exceptions.ResourceAlreadyExistsException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.AnswerRepository;
import com.youquiz.repositories.AnswerValidationRepository;
import com.youquiz.services.interfaces.IAnswerService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerValidationRepository avRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, AnswerValidationRepository avRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.avRepository = avRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnswerDTO create(AnswerRequestDTO request) {
        if (answerRepository.existsByAnswerIgnoreCase(request.getAnswer())) {
            throw new ResourceAlreadyExistsException("This answer already exists.");
        }

        Answer mappedAnswer = modelMapper.map(request, Answer.class);
        Answer createdAnswer = answerRepository.save(mappedAnswer);

        return modelMapper.map(createdAnswer, AnswerDTO.class);
    }

    @Override
    public AnswerDTO update(AnswerRequestDTO request) {
        Answer answer = answerRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("Answer not found."));

        answer.setAnswer(request.getAnswer());

        return modelMapper.map(answerRepository.save(answer), AnswerDTO.class);
    }

    @Override
    public AnswerDTO get(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Answer not found."));
        AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);
        List<AnswerValidation> av = avRepository.findAllByAnswerId(id);
        List<AnswerValidationDTO> avDto = av.stream().map(a -> modelMapper.map(a, AnswerValidationDTO.class)).toList();

        answerDTO.setAnswerValidations(avDto);

        return answerDTO;
    }

    @Override
    public AnswerDTO delete(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Answer not found."));

        answerRepository.deleteById(id);

        return modelMapper.map(answer, AnswerDTO.class);
    }

    @Override
    public Page<AnswerDTO> getAll(Map<String, Object> params) {
        String text = (String) params.get("text");
        Pageable pageable = Utilities.managePagination(params);

        Page<Answer> answers = answerRepository.findAllByAnswerLikeIgnoreCase("%" + text + "%", pageable);

        return answers.map(an -> modelMapper.map(an, AnswerDTO.class));
    }
}

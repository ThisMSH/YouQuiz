package com.youquiz.services;

import com.youquiz.dto.requestdto.AnswerRequestDTO;
import com.youquiz.dto.responsedto.AnswerDTO;
import com.youquiz.entities.Answer;
import com.youquiz.exceptions.ResourceAlreadyExistsException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.AnswerRepository;
import com.youquiz.services.interfaces.IAnswerService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
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

        return modelMapper.map(answer, AnswerDTO.class);
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

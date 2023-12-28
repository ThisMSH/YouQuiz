package com.youquiz.services;

import com.youquiz.dto.requestdto.QuizRequestDTO;
import com.youquiz.dto.responsedto.QuizDTO;
import com.youquiz.entities.Quiz;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.QuizRepository;
import com.youquiz.repositories.TrainerRepository;
import com.youquiz.services.interfaces.IQuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuizService implements IQuizService {
    private final QuizRepository quizRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuizService(QuizRepository quizRepository, TrainerRepository trainerRepository, ModelMapper modelMapper) {
        this.quizRepository = quizRepository;
        this.trainerRepository = trainerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QuizDTO create(QuizRequestDTO request) {
        if (!trainerRepository.existsById(request.getTrainerId())) {
            throw new ResourceNotFoundException("Trainer not found.");
        }

        Quiz quiz = quizRepository.save(request, )
    }

    @Override
    public QuizDTO update(QuizRequestDTO request) {
        return null;
    }

    @Override
    public QuizDTO delete(Long aLong) {
        return null;
    }

    @Override
    public QuizDTO get(Long aLong) {
        return null;
    }

    @Override
    public Page<QuizDTO> getAll(Map<String, Object> params) {
        return null;
    }
}

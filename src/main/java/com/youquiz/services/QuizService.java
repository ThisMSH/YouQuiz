package com.youquiz.services;

import com.youquiz.dto.requestdto.QuizRequestDTO;
import com.youquiz.dto.responsedto.QuizDTO;
import com.youquiz.entities.Quiz;
import com.youquiz.entities.Trainer;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.QuizRepository;
import com.youquiz.repositories.TrainerRepository;
import com.youquiz.services.interfaces.IQuizService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Quiz quiz = quizRepository.save(modelMapper.map(request, Quiz.class));

        return modelMapper.map(quiz, QuizDTO.class);
    }

    @Override
    public QuizDTO update(QuizRequestDTO request) {
        Quiz quiz = quizRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found."));
        Trainer trainer = trainerRepository.findById(request.getTrainerId())
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found."));

        quiz.setTitle(request.getTitle());
        quiz.setDuration(request.getDuration());
        quiz.setSuccessScore(request.getSuccessScore());
        quiz.setCanSeeAnswers(request.getCanSeeAnswers());
        quiz.setCanSeeResult(request.getCanSeeResult());
        quiz.setChances(request.getChances());
        quiz.setRemark(request.getRemark());
        quiz.setTrainer(trainer);

        Quiz updatedQuiz = quizRepository.save(quiz);

        return modelMapper.map(updatedQuiz, QuizDTO.class);
    }

    @Override
    public QuizDTO delete(Long id) {
        Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found."));

        quizRepository.deleteById(id);

        return modelMapper.map(quiz, QuizDTO.class);
    }

    @Override
    public QuizDTO get(Long id) {
        Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Quiz not found."));

        return modelMapper.map(quiz, QuizDTO.class);
    }

    @Override
    public Page<QuizDTO> getAll(Map<String, Object> params) {
        String title = (String) params.get("title");
        Pageable pageable = Utilities.managePagination(params);

        Page<Quiz> quizzes = quizRepository.findAllByTitleLikeIgnoreCase("%" + title + "%", pageable);

        return quizzes.map(quiz -> modelMapper.map(quiz, QuizDTO.class));
    }
}

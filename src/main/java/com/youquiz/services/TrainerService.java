package com.youquiz.services;

import com.youquiz.dto.requestdto.TrainerRequestDTO;
import com.youquiz.dto.responsedto.QuizDTO;
import com.youquiz.dto.responsedto.TrainerDTO;
import com.youquiz.entities.Quiz;
import com.youquiz.entities.Trainer;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.TrainerRepository;
import com.youquiz.services.interfaces.ITrainerService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TrainerService implements ITrainerService {
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, ModelMapper modelMapper) {
        this.trainerRepository = trainerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainerDTO create(TrainerRequestDTO request) {
        Trainer trainer = trainerRepository.save(modelMapper.map(request, Trainer.class));

        return modelMapper.map(trainer, TrainerDTO.class);
    }

    @Override
    public TrainerDTO update(TrainerRequestDTO request) {
        Trainer trainer = trainerRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found."));

        trainer.setName(request.getName());
        trainer.setFamilyName(request.getFamilyName());
        trainer.setAddress(request.getAddress());
        trainer.setBirthdate(request.getBirthdate());
        trainer.setSpeciality(request.getSpeciality());

        Trainer updatedTrainer = trainerRepository.save(trainer);

        return modelMapper.map(updatedTrainer, TrainerDTO.class);
    }

    @Override
    public TrainerDTO delete(Long id) {
        Trainer trainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found."));

        trainerRepository.deleteById(id);

        return modelMapper.map(trainer, TrainerDTO.class);
    }

    @Override
    public TrainerDTO get(Long id) {
        Trainer trainer = trainerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trainer not found."));

        TrainerDTO trainerDto = modelMapper.map(trainer, TrainerDTO.class);

        List<Quiz> quizzes = trainer.getQuizzes();

        if (!quizzes.isEmpty()) {
            List<QuizDTO> quizzesDto = quizzes.stream()
                .map(quiz -> modelMapper.map(quiz, QuizDTO.class))
                .toList();
            trainerDto.setQuizzes(quizzesDto);
        }

        return trainerDto;
    }

    @Override
    public Page<TrainerDTO> getAll(Map<String, Object> params) {
        String fullName = (String) params.get("fullName");
        Pageable pageable = Utilities.managePagination(params);

        Page<Trainer> trainers = trainerRepository.findAllByFullName(fullName, pageable);

        return trainers.map(trainer -> modelMapper.map(trainer, TrainerDTO.class));
    }
}

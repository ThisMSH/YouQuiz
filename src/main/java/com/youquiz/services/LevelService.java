package com.youquiz.services;

import com.youquiz.dto.requestdto.LevelRequestDTO;
import com.youquiz.dto.responsedto.LevelDTO;
import com.youquiz.dto.responsedto.QuestionDTO;
import com.youquiz.entities.Level;
import com.youquiz.entities.Question;
import com.youquiz.exceptions.ResourceAlreadyExistsException;
import com.youquiz.exceptions.ResourceBadRequestException;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.LevelRepository;
import com.youquiz.repositories.QuestionRepository;
import com.youquiz.services.interfaces.ILevelService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LevelService implements ILevelService {
    private final LevelRepository levelRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LevelService(LevelRepository levelRepository, QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.levelRepository = levelRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LevelDTO create(LevelRequestDTO request) {
        if (levelRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new ResourceAlreadyExistsException("The level \"" + request.getTitle() + "\" already exists.");
        }

        if (request.getMaxPoints() <= request.getMinPoints()) {
            throw new ResourceBadRequestException("Minimum points cannot be higher or equal to maximum points.");
        }

        Level level = levelRepository.save(modelMapper.map(request, Level.class));

        return modelMapper.map(level, LevelDTO.class);
    }

    @Override
    public LevelDTO update(LevelRequestDTO request) {
        Level level = levelRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("Level not found."));

        if (request.getMaxPoints() <= request.getMinPoints()) {
            throw new ResourceBadRequestException("Minimum points cannot be higher or equal to maximum points.");
        }

        level.setTitle(request.getTitle());
        level.setDescription(request.getDescription());
        level.setMaxPoints(request.getMaxPoints());
        level.setMinPoints(request.getMinPoints());

        Level updatedLevel = levelRepository.save(level);

        return modelMapper.map(updatedLevel, LevelDTO.class);
    }

    @Override
    public LevelDTO delete(Long id) {
        Level level = levelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Level not found."));

        levelRepository.deleteById(id);

        return modelMapper.map(level, LevelDTO.class);
    }

    @Override
    public LevelDTO get(Long id) {
        Level level = levelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Level not found."));
        LevelDTO levelDto = modelMapper.map(level, LevelDTO.class);
        List<Question> questions = questionRepository.findAllByLevelId(id);
        List<QuestionDTO> questionDtos = questions.stream().map(q -> modelMapper.map(q, QuestionDTO.class)).toList();

        levelDto.setQuestions(questionDtos);

        return levelDto;
    }

    @Override
    public Page<LevelDTO> getAll(Map<String, Object> params) {
        String title = (String) params.get("title");
        Pageable pageable = Utilities.managePagination(params);

        Page<Level> levels = levelRepository.findAllByTitleLikeIgnoreCase("%" + title + "%", pageable);

        return levels.map(lvl -> modelMapper.map(lvl, LevelDTO.class));
    }
}

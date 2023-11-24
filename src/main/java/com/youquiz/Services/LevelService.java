package com.youquiz.Services;

import com.youquiz.DTO.AltDTO.LevelAltDTO;
import com.youquiz.Entities.Level;
import com.youquiz.Exceptions.ResourceAlreadyExists;
import com.youquiz.Exceptions.ResourceBadRequest;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.LevelRepository;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {
    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LevelService(LevelRepository levelRepository, ModelMapper modelMapper) {
        this.levelRepository = levelRepository;
        this.modelMapper = modelMapper;
    }

    public Level createLevel(Level level) {
        if (levelRepository.existsByTitleIgnoreCase(level.getTitle())) {
            throw new ResourceAlreadyExists("The level \"" + level.getTitle() + "\" already exists.");
        }

        if (level.getMaxPoints() <= level.getMinPoints()) {
            throw new ResourceBadRequest("Minimum points cannot be higher or equal to maximum points.");
        }

        if (level.getMaxPoints() == 0 || level.getMinPoints() == 0) {
            throw new ResourceBadRequest("The points cannot equal 0.");
        }

        return levelRepository.save(level);
    }

    public Level getLevel(Long id) {
        Optional<Level> level = levelRepository.findById(id);

        return level.orElseThrow(() -> new ResourceNotFoundException("Level not found."));
    }

    public Integer deleteLevel(Long id) {
        if (!levelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Level not found.");
        }

        levelRepository.deleteById(id);

        return 1;
    }

    public Page<LevelAltDTO> getAllLevels(String title, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        Page<Level> levels = levelRepository.findAllByTitleLikeIgnoreCase("%" + title + "%", pageable);

        Page<LevelAltDTO> levelDTOs = levels.map(lvl -> modelMapper.map(lvl, LevelAltDTO.class));

        if (!levels.hasContent()) {
            String message = "";
            if (levels.getTotalPages() > 0 && (page + 1) > levels.getTotalPages()) {
                message = "No levels found in page " + (page + 1) + ".";
            } else {
                message = "No level was found.";
            }
            throw new ResourceNotFoundException(message);
        }

        return levelDTOs;
    }
}

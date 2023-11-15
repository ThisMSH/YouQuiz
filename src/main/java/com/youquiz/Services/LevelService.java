package com.youquiz.Services;

import com.youquiz.Entities.Level;
import com.youquiz.Exceptions.ResourceAlreadyExists;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {
    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Level createLevel(Level level) {
        if (levelRepository.existsByTitleIgnoreCase(level.getTitle())) {
            throw new ResourceAlreadyExists("The level \"" + level.getTitle() + "\" already exists.");
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

    public List<Level> getLevelsByTitle(String title) {
        return levelRepository.findByTitleLikeIgnoreCase("%" + title + "%");
    }
}

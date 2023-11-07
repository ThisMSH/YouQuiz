package com.youquiz.Services;

import com.youquiz.Entities.Level;
import com.youquiz.Repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelService {
    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public String createLevel(Level level) {
        // Add validation later
        levelRepository.save(level);
        return "The level \"" + level.getTitle() + "\" have been created successfully.";
    }

    public String deleteLevel(Long id) {
        // Check if the level exist
        levelRepository.deleteById(id);
        return "The level have been deleted successfully.";
    }

    public Level getLevel(Long id) {
        // Add better management for this method
        Optional<Level> level = levelRepository.findById(id);
        return level.orElseThrow();
    }
}

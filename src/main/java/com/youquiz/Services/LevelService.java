package com.youquiz.Services;

import com.youquiz.Entities.Level;
import com.youquiz.Repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {
    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public String createLevel(Level level) {
        levelRepository.save(level);
        return "The level \"" + level.getTitle() + "\" have been created successfully.";
    }
}

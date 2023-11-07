package com.youquiz.Controllers;

import com.youquiz.Entities.Level;
import com.youquiz.Services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/levels")
public class LevelController {
    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @PostMapping("/add")
    public String createLevel(@RequestBody Level level) {
        return levelService.createLevel(level);
    }
}

package com.youquiz.Controllers;

import com.youquiz.Entities.Level;
import com.youquiz.Services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelController {
    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("/{id}")
    public Level getLevel(@PathVariable("id") Long id) {
        return levelService.getLevel(id);
    }

    @GetMapping("/search/{title}")
    public List<Level> getLevelsByTitle(@PathVariable String title) {
        return levelService.getLevelsByTitle(title);
    }

    @PostMapping("/add")
    public Level createLevel(@RequestBody Level level) {
        return levelService.createLevel(level);
    }

    @DeleteMapping("/{id}")
    public Integer deleteLevel(@PathVariable("id") Long id) {
        return levelService.deleteLevel(id);
    }
}

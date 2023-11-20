package com.youquiz.Controllers;

import com.youquiz.Entities.Level;
import com.youquiz.Services.LevelService;
import com.youquiz.Utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getLevel(@PathVariable("id") Long id) {
        Level level = levelService.getLevel(id);

        return ResponseHandler.success(
            "The level has been fetched successfully.",
            HttpStatus.OK,
            level
        );
    }

    @GetMapping("/search/{title}")
    public List<Level> getLevelsByTitle(@PathVariable String title) {
        return levelService.getLevelsByTitle(title);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createLevel(@RequestBody @Valid Level level) {
        Level createdLevel = levelService.createLevel(level);

        return ResponseHandler.success(
            "The level has been created successfully.",
            HttpStatus.CREATED,
            createdLevel
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLevel(@PathVariable("id") Long id) {
        int del = levelService.deleteLevel(id);

        return ResponseHandler.success(
            "The level has been deleted successfully.",
            HttpStatus.OK,
            del
        );
    }
}

package com.youquiz.controllers;

import com.youquiz.entities.Level;
import com.youquiz.services.LevelService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
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

    @GetMapping
    public ResponseEntity<Object> getLevelsByTitle(
        @RequestParam(defaultValue = "") String title,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        var levels = levelService.getAllLevels(title, page - 1, size, sortBy, sortOrder);

        return ResponseHandler.success(
            "The levels have been fetched successfully.",
            HttpStatus.OK,
            levels
        );
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

    @PutMapping("/update")
    public ResponseEntity<Object> updateLevel(@RequestBody @Valid Level level) {
        Level updatedLevel = levelService.updateLevel(level);

        return ResponseHandler.success(
            "The level has been updated successfully.",
            HttpStatus.OK,
            updatedLevel
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
package com.youquiz.controllers;

import com.youquiz.dto.requestdto.LevelRequestDTO;
import com.youquiz.dto.responsedto.LevelDTO;
import com.youquiz.services.LevelService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        LevelDTO level = levelService.get(id);

        return ResponseHandler.success(
            "The level has been fetched successfully.",
            HttpStatus.OK,
            level
        );
    }

    @GetMapping
    public ResponseEntity<Object> getLevelsByTitle(
        @RequestParam(defaultValue = "") String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("title", title);

        var levels = levelService.getAll(params);

        return ResponseHandler.success(
            "The levels have been fetched successfully.",
            HttpStatus.OK,
            levels
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createLevel(@RequestBody @Valid LevelRequestDTO level) {
        LevelDTO createdLevel = levelService.create(level);

        return ResponseHandler.success(
            "The level has been created successfully.",
            HttpStatus.CREATED,
            createdLevel
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateLevel(@RequestBody @Valid LevelRequestDTO level) {
        LevelDTO updatedLevel = levelService.update(level);

        return ResponseHandler.success(
            "The level has been updated successfully.",
            HttpStatus.OK,
            updatedLevel
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLevel(@PathVariable("id") Long id) {
        LevelDTO deletedLevel = levelService.delete(id);

        return ResponseHandler.success(
            "The level has been deleted successfully.",
            HttpStatus.OK,
            deletedLevel
        );
    }
}

package com.youquiz.controllers;

import com.youquiz.dto.requestdto.TrainerRequestDTO;
import com.youquiz.services.TrainerService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTrainer(@PathVariable Long id) {
        var trainer = trainerService.get(id);

        return ResponseHandler.success(
            "The trainer has been fetched successfully.",
            HttpStatus.OK,
            trainer
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllTrainers(
        @RequestParam(defaultValue = "") String fullName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("fullName", fullName);
        var trainers = trainerService.getAll(params);

        return ResponseHandler.success(
            "The trainers have been fetched successfully.",
            HttpStatus.OK,
            trainers
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createTrainer(@RequestBody @Valid TrainerRequestDTO t) {
        var trainer = trainerService.create(t);

        return ResponseHandler.success(
            "The trainer has been created successfully.",
            HttpStatus.CREATED,
            trainer
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateTrainer(@RequestBody @Valid TrainerRequestDTO t) {
        var trainer = trainerService.update(t);

        return ResponseHandler.success(
            "The trainer has been updated successfully.",
            HttpStatus.OK,
            trainer
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrainer(@PathVariable Long id) {
        var trainer = trainerService.delete(id);

        return ResponseHandler.success(
            "The trainer has been deleted successfully.",
            HttpStatus.OK,
            trainer
        );
    }
}

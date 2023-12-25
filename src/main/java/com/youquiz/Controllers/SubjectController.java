package com.youquiz.Controllers;

import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Services.SubjectService;
import com.youquiz.Utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubject(@PathVariable("id") Long id) {
        Subject subject = subjectService.getSubject(id);

        return ResponseHandler.success(
            "The subject has been fetched successfully.",
            HttpStatus.OK,
            subject
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllSubjects(
        @RequestParam(defaultValue = "") String title,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        var subjects = subjectService.getAllSubjects(title, page - 1, size, sortBy, sortOrder);

        return ResponseHandler.success(
            "The subjects have been fetched successfully.",
            HttpStatus.OK,
            subjects
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createSubject(@RequestBody @Valid SubjectDTO subject) {
        Subject createdSubject = subjectService.createSubject(subject);

        return ResponseHandler.success(
            "The subject has been created successfully.",
            HttpStatus.CREATED,
            createdSubject
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateSubject(@RequestBody @Valid SubjectDTO subject) {
        Subject updatedSubject = subjectService.updateSubject(subject);

        return ResponseHandler.success(
            "The subject has been updated successfully.",
            HttpStatus.OK,
            updatedSubject
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubject(@PathVariable("id") Long id) {
        int del = subjectService.deleteSubject(id);

        return ResponseHandler.success(
            "The subject has been deleted successfully.",
            HttpStatus.CREATED,
            del
        );
    }
}

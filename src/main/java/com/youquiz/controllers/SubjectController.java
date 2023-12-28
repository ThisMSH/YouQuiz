package com.youquiz.controllers;

import com.youquiz.dto.requestdto.SubjectRequestDTO;
import com.youquiz.dto.responsedto.SubjectDTO;
import com.youquiz.services.SubjectService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        SubjectDTO subject = subjectService.get(id);

        return ResponseHandler.success(
            "The subject has been fetched successfully.",
            HttpStatus.OK,
            subject
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllSubjects(
        @RequestParam(defaultValue = "") String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("title", title);

        var subjects = subjectService.getAll(params);

        return ResponseHandler.success(
            "The subjects have been fetched successfully.",
            HttpStatus.OK,
            subjects
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createSubject(@RequestBody @Valid SubjectRequestDTO subject) {
        SubjectDTO createdSubject = subjectService.create(subject);

        return ResponseHandler.success(
            "The subject has been created successfully.",
            HttpStatus.CREATED,
            createdSubject
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateSubject(@RequestBody @Valid SubjectRequestDTO subject) {
        SubjectDTO updatedSubject = subjectService.update(subject);

        return ResponseHandler.success(
            "The subject has been updated successfully.",
            HttpStatus.OK,
            updatedSubject
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubject(@PathVariable("id") Long id) {
        SubjectDTO deletedSubject = subjectService.delete(id);

        return ResponseHandler.success(
            "The subject has been deleted successfully.",
            HttpStatus.OK,
            deletedSubject
        );
    }
}

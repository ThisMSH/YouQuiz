package com.youquiz.Controllers;

import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Services.SubjectService;
import com.youquiz.Utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<Object> createSubject(@RequestBody SubjectDTO subject) {
        Subject createdSubject = subjectService.createSubject(subject);

        return ResponseHandler.success(
            "The subject has been created successfully.",
            HttpStatus.CREATED,
            createdSubject
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

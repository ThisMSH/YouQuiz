package com.youquiz.controllers;

import com.youquiz.dto.requestdto.StudentRequestDTO;
import com.youquiz.services.StudentService;
import com.youquiz.utils.ResponseHandler;
import com.youquiz.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable Long id) {
        var student = studentService.get(id);

        return ResponseHandler.success(
            "The student has been fetched successfully.",
            HttpStatus.OK,
            student
        );
    }

    @GetMapping
    public ResponseEntity<Object> getAllStudents(
        @RequestParam(defaultValue = "") String fullName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "24") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder
    ) {
        Map<String, Object> params = Utilities.params(page, size, sortBy, sortOrder);
        params.put("fullName", fullName);
        var students = studentService.getAll(params);

        return ResponseHandler.success(
            "The students have been fetched successfully.",
            HttpStatus.OK,
            students
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createStudent(@RequestBody @Valid StudentRequestDTO s) {
        var student = studentService.create(s);

        return ResponseHandler.success(
            "The student has been created successfully.",
            HttpStatus.CREATED,
            student
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateStudent(@RequestBody @Valid StudentRequestDTO s) {
        var student = studentService.update(s);

        return ResponseHandler.success(
            "The student has been updated successfully.",
            HttpStatus.OK,
            student
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        var student = studentService.delete(id);

        return ResponseHandler.success(
            "The student has been deleted successfully.",
            HttpStatus.OK,
            student
        );
    }
}

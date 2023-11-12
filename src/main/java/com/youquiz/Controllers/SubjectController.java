package com.youquiz.Controllers;

import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Subject getSubject(@PathVariable("id") Long id) {
        return subjectService.getSubject(id);
    }

    @PostMapping("/add")
    public String createSubject(@RequestBody SubjectDTO subject) {
        return subjectService.createSubject(subject);
    }
}

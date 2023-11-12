package com.youquiz.Controllers;

import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/add")
    public String createSubject(@RequestBody SubjectDTO subject) {
        return subjectService.createSubject(subject);
    }
}

package com.youquiz.Services;

import com.youquiz.Entities.Subject;
import com.youquiz.Repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public String createSubject(Subject subject) {
        // Add validation later
        subjectRepository.save(subject);
        return "The subject \"" + subject.getTitle() + "\" has been created successfully.";
    }
}

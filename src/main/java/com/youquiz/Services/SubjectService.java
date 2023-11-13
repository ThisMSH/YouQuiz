package com.youquiz.Services;

import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    public String createSubject(SubjectDTO s) {
        // Add validation later
        Subject subject = modelMapper.map(s, Subject.class);

        if (s.getParentId() != null) {
            Subject parent = subjectRepository.findById(s.getParentId()).get();
            subject.setParent(parent);
        }

        subjectRepository.save(subject);
        return "The subject \"" + subject.getTitle() + "\" has been created successfully.";
    }

    public Subject getSubject(Long id) {
        // Add better management for this method
//        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new SubjectNotFoundException(id));
        Optional<Subject> subject = subjectRepository.findById(id);
        return subject.orElseThrow();
    }

    public String deleteSubject(Long id) {
        // Check if subject exist
        Subject subject = subjectRepository.findById(id).get();

        for (Subject child : subject.getChildren()) {
            if (subject.getParent() != null) {
                child.setParent(subject.getParent());
            } else {
                child.setParent(null);
            }
        }

        subjectRepository.saveAll(subject.getChildren());
        subjectRepository.deleteById(id);
        return "The subject has been deleted successfully.";
    }
}

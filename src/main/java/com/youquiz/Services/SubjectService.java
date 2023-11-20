package com.youquiz.Services;

import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Exceptions.ResourceNotFoundException;
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

    public Subject createSubject(SubjectDTO s) {
        Subject subject = modelMapper.map(s, Subject.class);

        return subjectRepository.save(subject);
    }

    public Subject getSubject(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);

        return subject.orElseThrow(() -> new ResourceNotFoundException("Subject not found."));
    }

    public Integer deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found.");
        }

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

        return 1;
    }
}

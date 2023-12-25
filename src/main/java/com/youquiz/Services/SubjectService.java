package com.youquiz.Services;

import com.youquiz.DTO.AltDTO.SubjectAltDTO;
import com.youquiz.DTO.SubjectDTO;
import com.youquiz.Entities.Subject;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Repositories.SubjectRepository;
import com.youquiz.Utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (s.getParentId() != null && !subjectRepository.existsById(s.getParentId())) {
            throw new ResourceNotFoundException("Parent subject does not exist.");
        }

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

    public Page<SubjectAltDTO> getAllSubjects(String title, int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = Utilities.managePagination(page, size, sortBy, sortOrder);

        Page<Subject> subjects = subjectRepository.findAllByTitleLikeIgnoreCase("%" + title + "%", pageable);

        Page<SubjectAltDTO> subjectDTOs = subjects.map(subj -> modelMapper.map(subj, SubjectAltDTO.class));

        if (!subjects.hasContent()) {
            String message = "";
            if (subjects.getTotalPages() > 0 && (page + 1) > subjects.getTotalPages()) {
                message = "No subjects found in page " + (page + 1) + ".";
            } else {
                message = "No subject was found.";
            }
            throw new ResourceNotFoundException(message);
        }

        return subjectDTOs;
    }

    public Subject updateSubject(SubjectDTO s) {
        Subject subject = subjectRepository.findById(s.getId()).orElseThrow(() -> new ResourceNotFoundException("Subject does not exist."));

        if (s.getParentId() != null && !subjectRepository.existsById(s.getParentId())) {
            throw new ResourceNotFoundException("Parent subject does not exist.");
        }

        Subject subParent = new Subject();

        if (s.getParentId() != null) {
            subParent.setId(s.getParentId());
            subject.setParent(subParent);
        } else {
            subject.setParent(null);
        }

        subject.setId(s.getId());
        subject.setTitle(s.getTitle());

        return subjectRepository.save(subject);
    }
}

package com.youquiz.services;

import com.youquiz.dto.requestdto.SubjectRequestDTO;
import com.youquiz.dto.responsedto.SubjectDTO;
import com.youquiz.entities.Subject;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.SubjectRepository;
import com.youquiz.services.interfaces.ISubjectService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubjectDTO create(SubjectRequestDTO request) {
        if (request.getParentId() != null && !subjectRepository.existsById(request.getParentId())) {
            throw new ResourceNotFoundException("Parent subject not found.");
        }

        Subject subject = modelMapper.map(request, Subject.class);

        return modelMapper.map(subjectRepository.save(subject), SubjectDTO.class);
    }

    @Override
    public SubjectDTO update(SubjectRequestDTO request) {
        Subject subject = subjectRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));

        if (request.getParentId() != null && !subjectRepository.existsById(request.getParentId())) {
            throw new ResourceNotFoundException("Parent subject not found.");
        }

        if (request.getParentId() != null) {
            Subject parent = new Subject();
            parent.setId(request.getParentId());

            subject.setParent(parent);
        } else {
            subject.setParent(null);
        }

        subject.setTitle(request.getTitle());

        return modelMapper.map(subjectRepository.save(subject), SubjectDTO.class);
    }

    @Override
    public SubjectDTO delete(Long id) {
        Subject subject = subjectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));

        for (Subject child : subject.getChildren()) {
            if (subject.getParent() != null) {
                child.setParent(subject.getParent());
            } else {
                child.setParent(null);
            }
        }

        subjectRepository.saveAll(subject.getChildren());
        subjectRepository.deleteById(id);

        return modelMapper.map(subject, SubjectDTO.class);
    }

    @Override
    public SubjectDTO get(Long id) {
        Subject subject = subjectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));

        return modelMapper.map(subject, SubjectDTO.class);
    }

    @Override
    public Page<SubjectDTO> getAll(Map<String, Object> params) {
        String title = (String) params.get("title");
        Pageable pageable = Utilities.managePagination(params);

        Page<Subject> subjects = subjectRepository.findAllByTitleLikeIgnoreCase("%" + title + "%", pageable);

        return subjects.map(subj -> modelMapper.map(subj, SubjectDTO.class));
    }
}

package com.youquiz.services;

import com.youquiz.dto.requestdto.StudentRequestDTO;
import com.youquiz.dto.responsedto.QuizAssignmentDTO;
import com.youquiz.dto.responsedto.StudentDTO;
import com.youquiz.entities.QuizAssignment;
import com.youquiz.entities.Student;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.repositories.StudentRepository;
import com.youquiz.services.interfaces.IStudentService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentDTO create(StudentRequestDTO request) {
        Student student = studentRepository.save(modelMapper.map(request, Student.class));

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentDTO update(StudentRequestDTO request) {
        Student student = studentRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        student.setName(request.getName());
        student.setFamilyName(request.getFamilyName());
        student.setAddress(request.getAddress());
        student.setBirthdate(request.getBirthdate());
        student.setRegistrationDate(request.getRegistrationDate());

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    @Override
    public StudentDTO delete(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        studentRepository.deleteById(id);

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentDTO get(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        StudentDTO studentDto = modelMapper.map(student, StudentDTO.class);

        List<QuizAssignment> quizAssignments = student.getQuizAssignments();

        if (!quizAssignments.isEmpty()) {
            List<QuizAssignmentDTO> quizAssignmentsDto = quizAssignments.stream()
                .map(qa -> modelMapper.map(qa, QuizAssignmentDTO.class))
                .toList();
            studentDto.setQuizAssignments(quizAssignmentsDto);
        }

        return studentDto;
    }

    @Override
    public Page<StudentDTO> getAll(Map<String, Object> params) {
        String fullName = (String) params.get("fullName");
        Pageable pageable = Utilities.managePagination(params);

        Page<Student> students = studentRepository.findAllByFullName(fullName, pageable);

        return students.map(student -> modelMapper.map(student, StudentDTO.class));
    }
}

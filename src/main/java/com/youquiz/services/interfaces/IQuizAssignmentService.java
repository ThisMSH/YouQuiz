package com.youquiz.services.interfaces;

import com.youquiz.dto.requestdto.QuizAssignmentRequestDTO;
import com.youquiz.dto.responsedto.QuizAssignmentDTO;

import java.util.List;

public interface IQuizAssignmentService extends GenericService<QuizAssignmentDTO, QuizAssignmentRequestDTO, Long> {
    QuizAssignmentDTO getWithResults(Long id);
    List<QuizAssignmentDTO> getByStudentWithResults(Long studentId);
    QuizAssignmentDTO closeQuizAssignment(Long id);
    List<QuizAssignmentDTO> getByStudent(Long studentId);
    QuizAssignmentDTO saveSelectedAnswer(Long answerValidationId, Long quizAssignmentId);
}

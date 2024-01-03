package com.youquiz.repositories;

import com.youquiz.entities.QuizAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizAssignmentRepository extends JpaRepository<QuizAssignment, Long> {
    Optional<QuizAssignment> findByStudentIdAndQuizId(Long studentId, Long quizId);
    List<QuizAssignment> findAllByStudentId(Long studentId);
}

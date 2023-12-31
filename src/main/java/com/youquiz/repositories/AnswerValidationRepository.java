package com.youquiz.repositories;

import com.youquiz.entities.AnswerValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerValidationRepository extends JpaRepository<AnswerValidation, Long> {
    Optional<AnswerValidation> findByQuestionIdAndAnswerId(Long questionId, Long answerId);
    Boolean existsByQuestionIdAndAnswerId(Long questionId, Long answerId);
    List<AnswerValidation> findAllByQuestionId(Long questionId);
    List<AnswerValidation> findAllByAnswerId(Long answerId);
    void deleteByQuestionIdAndAnswerId(Long questionId, Long answerId);
}

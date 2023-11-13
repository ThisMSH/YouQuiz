package com.youquiz.Repositories;

import com.youquiz.Entities.AnswerValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerValidationRepository extends JpaRepository<AnswerValidation, Long> {
    AnswerValidation findByQuestionIdAndAnswerId(Long questionId, Long answerId);
    void deleteByQuestionIdAndAnswerId(Long questionId, Long answerId);
}

package com.youquiz.repositories;

import com.youquiz.entities.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Boolean existsByAnswerIgnoreCase(String answer);
    Page<Answer> findAllByAnswerLikeIgnoreCase(String answer, Pageable pageable);
}

package com.youquiz.Repositories;

import com.youquiz.Entities.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Boolean existsByAnswerIgnoreCase(String answer);
    Page<Answer> findAllByAnswerLikeIgnoreCase(String answer, Pageable pageable);
}

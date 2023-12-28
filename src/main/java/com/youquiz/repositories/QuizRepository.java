package com.youquiz.repositories;

import com.youquiz.entities.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Page<Quiz> findAllByTitleLikeIgnoreCase(String title, Pageable pageable);
}

package com.youquiz.Repositories;

import com.youquiz.Entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByQuestionLikeIgnoreCase(String question, Pageable pageable);
}

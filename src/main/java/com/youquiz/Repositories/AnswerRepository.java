package com.youquiz.Repositories;

import com.youquiz.Entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAnswer(String answer);
}

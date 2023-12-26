package com.youquiz.repositories;

import com.youquiz.entities.QuizQuestion;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, QuizQuestionId> {}

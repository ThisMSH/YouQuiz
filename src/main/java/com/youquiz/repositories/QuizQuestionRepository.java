package com.youquiz.repositories;

import com.youquiz.entities.QuizQuestion;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, QuizQuestionId> {
    List<QuizQuestion> findAllByQuestionId(long questionId);
    List<QuizQuestion> findAllByQuizId(long quizId);
}

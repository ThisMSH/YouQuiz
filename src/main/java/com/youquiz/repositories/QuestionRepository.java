package com.youquiz.repositories;

import com.youquiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    List<Question> findAllByLevelId(Long levelId);
    List<Question> findAllBySubjectId(Long subjectId);
}

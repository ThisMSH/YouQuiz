package com.youquiz.Repositories;

import com.youquiz.Entities.Question;
import com.youquiz.Enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    Page<Question> findAllByQuestionLikeIgnoreCase(@Size(max = 255, message = "Question cannot exceed 255 characters.") String question, Pageable pageable);
    Page<Question> findAllByQuestionLikeIgnoreCaseAndType(@Size(max = 255, message = "Question cannot exceed 255 characters.") String question, QuestionType type, Pageable pageable);
    Page<Question> findAllByQuestionLikeIgnoreCaseAndLevelId(@Size(max = 255, message = "Question cannot exceed 255 characters.") String question, Long levelId, Pageable pageable);
    Page<Question> findAllByQuestionLikeIgnoreCaseAndTypeAndLevelId(@Size(max = 255, message = "Question cannot exceed 255 characters.") String question, QuestionType type, Long levelId, Pageable pageable);
}

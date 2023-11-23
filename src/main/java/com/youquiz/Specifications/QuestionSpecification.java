package com.youquiz.Specifications;

import com.youquiz.Entities.Question;
import com.youquiz.Enums.QuestionType;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecification {
    public static Specification<Question> questionContains(String question) {
        return (root, query, criteriaBuilder) -> {
            if (question.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("question")), "%" + question.toLowerCase() + "%");
        };
    }

    public static Specification<Question> typeEquals(QuestionType type) {
        return (root, query, criteriaBuilder) -> {
            if (type == null || type.toString().isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("type"), type);
        };
    }

    public static Specification<Question> getByLevel(Long levelId) {
        return (root, query, criteriaBuilder) -> {
            if (levelId == 0) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("level").get("id"), levelId);
        };
    }

    public static Specification<Question> getBySubject(Long subjectId) {
        return (root, query, criteriaBuilder) -> {
            if (subjectId == 0) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("subject").get("id"), subjectId);
        };
    }
}

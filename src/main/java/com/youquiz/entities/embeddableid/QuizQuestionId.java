package com.youquiz.entities.embeddableid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class QuizQuestionId implements Serializable {
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "question_id")
    private Long questionId;
}

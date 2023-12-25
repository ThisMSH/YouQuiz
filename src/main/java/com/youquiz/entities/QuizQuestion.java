package com.youquiz.entities;

import com.youquiz.entities.embeddableid.QuizQuestionId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {
    @EmbeddedId
    private QuizQuestionId id;

    @PositiveOrZero(message = "Timer cannot be negative.")
    @Column(nullable = false)
    private short timer = 0;

    @NotNull(message = "Allow partial points field is required.")
    @Column(nullable = false)
    private boolean allowPartialPoints;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @MapsId("quizId")
    private Quiz quiz;

    @ManyToOne
    @MapsId("questionId")
    private Question question;
}

package com.youquiz.dto.requestdto;

import com.youquiz.entities.embeddableid.QuizQuestionId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuizQuestionRequestDTO {
    private QuizQuestionId id;

    @NotNull(message = "Timer is required.")
    @PositiveOrZero(message = "Timer cannot be negative.")
    private Short timer;

    @NotNull(message = "Allow partial points field is required.")
    private Boolean allowPartialPoints;

    private LocalDateTime createdAt;

    @NotNull(message = "Quiz is required.")
    @Positive(message = "Quiz ID is invalid.")
    private Long quizId;

    @NotNull(message = "Question is required.")
    @Positive(message = "Question ID is invalid.")
    private Long questionId;
}

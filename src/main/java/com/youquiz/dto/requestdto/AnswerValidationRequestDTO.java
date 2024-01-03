package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerValidationRequestDTO {
    private Long id;

    @NotNull(message = "Points are required.")
    @DecimalMin(value = "0.0", message = "The points cannot be lower than 0.")
    private Double points;

    private LocalDateTime createdAt;

    @NotNull(message = "Answer is required.")
    @Positive(message = "Answer ID is invalid.")
    private Long answerId;

    @NotNull(message = "Question is required.")
    @Positive(message = "Question ID is invalid.")
    private Long questionId;

    private List<Long> quizAssignmentIds;
}

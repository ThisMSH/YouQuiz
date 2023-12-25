package com.youquiz.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerValidationDTO {
    @NotNull(message = "Points is required.")
    @DecimalMin(value = "0.0", message = "The points cannot be lower than 0.")
    @DecimalMax(value = "10.0", message = "The points cannot be higher than 10.")
    private double points;

    @NotNull(message = "Answer is required.")
    private Long answerId;

    @NotNull(message = "Question is required.")
    private Long questionId;
}

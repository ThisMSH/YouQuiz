package com.youquiz.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("answer-id")
    private Long answerId;

    @NotNull(message = "Question is required.")
    @JsonProperty("question-id")
    private Long questionId;
}

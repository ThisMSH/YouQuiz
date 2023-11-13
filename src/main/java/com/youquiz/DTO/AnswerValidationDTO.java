package com.youquiz.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerValidationDTO {
    private double points;

    @JsonProperty("answer-id")
    private Long answerId;

    @JsonProperty("question-id")
    private Long questionId;
}

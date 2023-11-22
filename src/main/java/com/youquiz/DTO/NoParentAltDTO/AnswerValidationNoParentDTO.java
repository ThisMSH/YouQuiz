package com.youquiz.DTO.NoParentAltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnswerValidationNoParentDTO {
    private Long id;

    private double points;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("answer-id")
    private Long answerId;

    @JsonProperty("question-id")
    private Long questionId;
}

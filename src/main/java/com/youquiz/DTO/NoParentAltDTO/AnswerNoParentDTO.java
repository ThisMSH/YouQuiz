package com.youquiz.DTO.NoParentAltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerNoParentDTO {
    private Long id;

    private String answer;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    private LocalDateTime updatedAt;

    @JsonProperty("answer-validation-ids")
    private List<Long> answerValidationIds;
}

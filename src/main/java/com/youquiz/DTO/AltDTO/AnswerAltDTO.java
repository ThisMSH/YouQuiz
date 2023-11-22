package com.youquiz.DTO.AltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.DTO.NoParentAltDTO.AnswerValidationNoParentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerAltDTO {
    private Long id;

    private String answer;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    private LocalDateTime updatedAt;

    private List<AnswerValidationNoParentDTO> answerValidations;
}

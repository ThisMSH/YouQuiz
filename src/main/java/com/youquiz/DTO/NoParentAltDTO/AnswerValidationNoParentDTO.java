package com.youquiz.DTO.NoParentAltDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnswerValidationNoParentDTO {
    private Long id;

    private double points;

    private LocalDateTime createdAt;

    private Long answerId;

    private Long questionId;
}

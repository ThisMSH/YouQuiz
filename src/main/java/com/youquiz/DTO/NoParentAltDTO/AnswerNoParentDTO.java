package com.youquiz.DTO.NoParentAltDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerNoParentDTO {
    private Long id;

    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> answerValidationIds;
}

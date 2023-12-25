package com.youquiz.DTO.AltDTO;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<AnswerValidationNoParentDTO> answerValidations;
}

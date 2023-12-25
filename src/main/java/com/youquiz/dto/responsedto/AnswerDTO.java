package com.youquiz.dto.responsedto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerDTO {
    private Long id;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AnswerValidationDTO> answerValidations;
}

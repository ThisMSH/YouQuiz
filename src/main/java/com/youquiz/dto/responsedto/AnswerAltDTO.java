package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.AnswerValidationNoParentDTO;
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

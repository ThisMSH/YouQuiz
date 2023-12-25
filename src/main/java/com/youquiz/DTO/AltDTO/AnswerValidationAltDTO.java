package com.youquiz.DTO.AltDTO;

import com.youquiz.DTO.NoParentAltDTO.AnswerNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.QuestionNoParentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnswerValidationAltDTO {
    private Long id;

    private double points;

    private LocalDateTime createdAt;

    private AnswerNoParentDTO answer;

    private QuestionNoParentDTO question;
}

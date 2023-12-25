package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.AnswerNoParentDTO;
import com.youquiz.dto.requestdto.QuestionNoParentDTO;
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

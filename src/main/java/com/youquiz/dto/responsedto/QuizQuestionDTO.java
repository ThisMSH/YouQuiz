package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.QuestionRequestDTO;
import com.youquiz.dto.requestdto.QuizRequestDTO;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuizQuestionDTO {
    private QuizQuestionId id;
    private short timer;
    private boolean allowPartialPoints;
    private LocalDateTime createdAt;
    private QuizRequestDTO quiz;
    private QuestionRequestDTO question;
}

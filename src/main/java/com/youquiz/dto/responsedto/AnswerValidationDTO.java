package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.AnswerRequestDTO;
import com.youquiz.dto.requestdto.QuestionRequestDTO;
import com.youquiz.dto.requestdto.QuizAssignmentRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerValidationDTO {
    private Long id;
    private double points;
    private LocalDateTime createdAt;
    private AnswerRequestDTO answer;
    private QuestionRequestDTO question;
    private List<QuizAssignmentRequestDTO> quizAssignments;
}

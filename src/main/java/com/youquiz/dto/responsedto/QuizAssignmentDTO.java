package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.QuizRequestDTO;
import com.youquiz.dto.requestdto.StudentRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuizAssignmentDTO {
    private Long id;
    private String reason;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private short attempt;
    private double score;
    private double passResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private StudentRequestDTO student;
    private QuizRequestDTO quiz;
    private List<AnswerValidationDTO> answerValidations;
}

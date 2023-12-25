package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.TrainerRequestDTO;
import com.youquiz.entities.Trainer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuizDTO {
    private Long id;
    private String title;
    private short duration;
    private double successScore;
    private boolean canSeeAnswers = false;
    private boolean canSeeResult = false;
    private byte chances;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TrainerRequestDTO trainer;
    private List<QuizAssignmentDTO> quizAssignments;
    private List<QuizQuestionDTO> quizQuestions;
}

package com.youquiz.dto.requestdto;

import com.youquiz.entities.Trainer;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuizRequestDTO {
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title cannot exceed 255 characters.")
    private String title;

    @NotNull(message = "Duration is required.")
    private Short duration;

    @NotNull(message = "Success score is required.")
    private Double successScore;

    private Boolean canSeeAnswers = false;

    private Boolean canSeeResult = false;

    @NotNull(message = "Chances field is required.")
    private Byte chances;

    @NotNull(message = "Remark is required.")
    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Trainer is required.")
    private Long trainerId;

    private List<Long> quizAssignmentIds;

    private List<QuizQuestionId> quizQuestionIds;
}

package com.youquiz.dto.requestdto;

import com.youquiz.entities.Trainer;
import com.youquiz.entities.embeddableid.QuizQuestionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive(message = "Chances of the quiz have to be greater than 0.")
    private Byte chances;

    @NotBlank(message = "Remark is required.")
    @Size(max = 255, message = "Remark cannot exceed 255 characters.")
    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Trainer is required.")
    @Positive(message = "Trainer ID is invalid.")
    private Long trainerId;

    private List<Long> quizAssignmentIds;

    private List<QuizQuestionId> quizQuestionIds;
}

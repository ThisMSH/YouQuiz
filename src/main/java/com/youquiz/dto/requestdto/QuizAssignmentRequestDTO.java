package com.youquiz.dto.requestdto;

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
public class QuizAssignmentRequestDTO {
    private Long id;

    @NotBlank(message = "Reason is required.")
    @Size(max = 255, message = "Reason field cannot exceed 255 characters.")
    private String reason;

    @NotNull(message = "Starting time is required.")
    private LocalDateTime startingTime;

    @NotNull(message = "Ending time is required.")
    private LocalDateTime endingTime;

    private Short attempt = 0;

    private Double score = 0.0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Student is required.")
    @Positive(message = "Student ID is invalid.")
    private Long studentId;

    @NotNull(message = "Quiz is required.")
    @Positive(message = "Quiz ID is invalid.")
    private Long quizId;

    private List<Long> answerValidationIds;
}

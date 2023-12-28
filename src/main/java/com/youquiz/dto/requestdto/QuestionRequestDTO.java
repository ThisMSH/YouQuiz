package com.youquiz.dto.requestdto;

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
public class QuestionRequestDTO {
    private Long id;

    @NotBlank(message = "Question is required.")
    @Size(max = 255, message = "Question cannot exceed 255 characters.")
    private String question;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    private String description;

    private Byte answersCount;

    private Byte correctAnswersCount;

    @NotBlank(message = "Question type is required.")
    private String type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Level is required.")
    @Positive(message = "Level ID is invalid.")
    private Long levelId;

    @NotNull(message = "Subject is required.")
    @Positive(message = "Subject ID is invalid.")
    private Long subjectId;

    private List<Long> mediaIds;

    private List<Long> answerValidationIds;

    private List<QuizQuestionId> quizQuestionIds;
}

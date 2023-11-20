package com.youquiz.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.Enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    @NotBlank(message = "Question is required.")
    @Size(max = 255, message = "Question cannot exceed 255 characters.")
    private String question;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    private String description;

    @JsonProperty("answers-count")
    private byte answersCount = 0;

    @JsonProperty("correct-answers-count")
    private byte correctAnswersCount = 0;

    @NotNull(message = "Question type is required.")
    private QuestionType type;

    @NotNull(message = "You must assign a level to this question.")
    @JsonProperty("level-id")
    private Long levelId;

    @NotNull(message = "You must assign a subject to this question.")
    @JsonProperty("subject-id")
    private Long subjectId;

    // Add a list of MediaDTO
}

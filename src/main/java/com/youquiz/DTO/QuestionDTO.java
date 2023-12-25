package com.youquiz.DTO;

import com.youquiz.Enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;

    @NotBlank(message = "Question is required.")
    @Size(max = 255, message = "Question cannot exceed 255 characters.")
    private String question;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    private String description;

    @NotNull(message = "Question type is required.")
    private QuestionType type;

    @NotNull(message = "You must assign a level to this question.")
    private Long levelId;

    @NotNull(message = "You must assign a subject to this question.")
    private Long subjectId;
}

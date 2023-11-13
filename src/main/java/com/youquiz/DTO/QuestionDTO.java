package com.youquiz.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.Enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private String question;

    private String description;

    @JsonProperty("answers-count")
    private byte answersCount;

    @JsonProperty("correct-answers-count")
    private byte correctAnswersCount;

    private QuestionType type;

    @JsonProperty("level-id")
    private Long levelId;

    @JsonProperty("subject-id")
    private Long subjectId;

    // Add a list of MediaDTO
}

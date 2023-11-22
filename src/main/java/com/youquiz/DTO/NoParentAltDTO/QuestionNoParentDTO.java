package com.youquiz.DTO.NoParentAltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.Enums.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionNoParentDTO {
    private Long id;

    private String question;

    private String description;

    @JsonProperty("answers-count")
    private byte answersCount;

    @JsonProperty("correct-answers-count")
    private byte correctAnswersCount;

    private QuestionType type;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    private LocalDateTime updatedAt;

    @JsonProperty("level-id")
    private Long levelId;

    @JsonProperty("subject-id")
    private Long subjectId;

    @JsonProperty("media-ids")
    private List<Long> mediaIds;

    @JsonProperty("answer-validation-ids")
    private List<Long> answerValidationIds;
}

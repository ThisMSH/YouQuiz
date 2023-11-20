package com.youquiz.DTO.Alt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.Entities.AnswerValidation;
import com.youquiz.Entities.Level;
import com.youquiz.Entities.Media;
import com.youquiz.Entities.Subject;
import com.youquiz.Enums.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionAltDTO {
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

    private Level level;

    private Subject subject;

    private List<Media> medias;

    private List<AnswerValidation> answerValidations;
}

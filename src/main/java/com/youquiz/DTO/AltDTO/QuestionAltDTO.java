package com.youquiz.DTO.AltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.DTO.NoParentAltDTO.AnswerValidationNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.LevelNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.MediaNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.SubjectNoParentDTO;
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

    private LevelNoParentDTO level;

    private SubjectNoParentDTO subject;

    private List<MediaNoParentDTO> medias;

    private List<AnswerValidationNoParentDTO> answerValidations;
}

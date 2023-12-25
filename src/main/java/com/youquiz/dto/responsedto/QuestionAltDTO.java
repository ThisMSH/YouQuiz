package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.AnswerValidationNoParentDTO;
import com.youquiz.dto.requestdto.LevelNoParentDTO;
import com.youquiz.dto.requestdto.MediaNoParentDTO;
import com.youquiz.dto.requestdto.SubjectNoParentDTO;
import com.youquiz.enums.QuestionType;
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

    private byte answersCount;

    private byte correctAnswersCount;

    private QuestionType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LevelNoParentDTO level;

    private SubjectNoParentDTO subject;

    private List<MediaNoParentDTO> medias;

    private List<AnswerValidationNoParentDTO> answerValidations;

    private List<AnswerValidationAltDTO> answerValidationsWithParent;
}

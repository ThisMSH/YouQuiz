package com.youquiz.dto.requestdto;

import com.youquiz.enums.QuestionType;
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

    private byte answersCount;

    private byte correctAnswersCount;

    private QuestionType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long levelId;

    private Long subjectId;

    private List<Long> mediaIds;

    private List<Long> answerValidationIds;
}

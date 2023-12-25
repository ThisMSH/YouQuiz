package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.LevelRequestDTO;
import com.youquiz.dto.requestdto.SubjectRequestDTO;
import com.youquiz.enums.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private String question;
    private String description;
    private byte answersCount;
    private byte correctAnswersCount;
    private QuestionType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LevelRequestDTO level;
    private SubjectRequestDTO subject;
    private List<MediaDTO> medias;
    private List<AnswerValidationDTO> answerValidations;
    private List<QuizQuestionDTO> quizQuestions;
}

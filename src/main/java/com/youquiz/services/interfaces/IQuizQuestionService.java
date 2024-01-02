package com.youquiz.services.interfaces;

import com.youquiz.dto.requestdto.QuizQuestionRequestDTO;
import com.youquiz.dto.responsedto.QuizQuestionDTO;
import com.youquiz.entities.embeddableid.QuizQuestionId;

import java.util.List;

public interface IQuizQuestionService extends GenericService<QuizQuestionDTO, QuizQuestionRequestDTO, QuizQuestionId> {
    List<QuizQuestionDTO> getAllByQuestion(Long questionId);
    List<QuizQuestionDTO> getAllByQuiz(Long quizId);
}

package com.youquiz.services.interfaces;

import com.youquiz.dto.requestdto.AnswerValidationRequestDTO;
import com.youquiz.dto.responsedto.AnswerValidationDTO;

import java.util.List;

public interface IAnswerValidationService extends GenericService<AnswerValidationDTO, AnswerValidationRequestDTO, Long> {
    AnswerValidationDTO delete(Long questionId, Long answerId);
    AnswerValidationDTO get(Long questionId, Long answerId);
    List<AnswerValidationDTO> getAssignedAnswersByQuestion(Long questionId);
    List<AnswerValidationDTO> getAssignedAnswersByAnswer(Long answerId);
}

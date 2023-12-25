package com.youquiz.dto.requestdto;

import com.youquiz.dto.responsedto.AnswerValidationDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AnswerRequestDTO {
    private Long id;

    @NotBlank(message = "Answer is required.")
    @Size(max = 255, message = "Answer cannot exceed 255 characters.")
    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> answerValidationIds;
}

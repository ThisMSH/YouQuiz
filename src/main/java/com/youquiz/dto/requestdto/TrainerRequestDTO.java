package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TrainerRequestDTO extends UserRequestDTO {
    @NotBlank(message = "Speciality is required.")
    @Size(max = 255, message = "Speciality cannot exceed 255 characters.")
    private String speciality;

    private List<Long> quizIds;
}

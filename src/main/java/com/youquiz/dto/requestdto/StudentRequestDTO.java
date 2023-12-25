package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StudentRequestDTO extends UserRequestDTO {
    @NotNull(message = "Registration date is required.")
    private LocalDate registrationDate;

    private List<Long> quizAssignmentIds;
}

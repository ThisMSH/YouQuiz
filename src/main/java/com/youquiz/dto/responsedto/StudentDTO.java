package com.youquiz.dto.responsedto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private LocalDate registrationDate;
    private List<QuizAssignmentDTO> quizAssignments;
}

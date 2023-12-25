package com.youquiz.dto.responsedto;

import com.youquiz.entities.Quiz;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TrainerDTO extends UserDTO {
    private String speciality;
    private List<Quiz> quizzes;
}

package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.SubjectRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<QuestionDTO> questions;
    private SubjectRequestDTO parent;
    private List<SubjectDTO> children;
}

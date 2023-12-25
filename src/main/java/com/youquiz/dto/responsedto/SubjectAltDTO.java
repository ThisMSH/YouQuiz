package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.QuestionNoParentDTO;
import com.youquiz.dto.requestdto.SubjectNoParentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectAltDTO {
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<QuestionNoParentDTO> questions;

    private SubjectNoParentDTO parent;

    private List<SubjectNoParentDTO> children;
}

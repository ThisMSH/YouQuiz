package com.youquiz.DTO.AltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youquiz.DTO.NoParentAltDTO.QuestionNoParentDTO;
import com.youquiz.DTO.NoParentAltDTO.SubjectNoParentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectAltDTO {
    private Long id;

    private String title;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    private LocalDateTime updatedAt;

    private List<QuestionNoParentDTO> questions;

    private SubjectNoParentDTO parent;

    private List<SubjectNoParentDTO> children;
}

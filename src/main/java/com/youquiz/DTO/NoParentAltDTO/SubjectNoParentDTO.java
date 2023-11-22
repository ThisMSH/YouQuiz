package com.youquiz.DTO.NoParentAltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectNoParentDTO {
    private Long id;

    private String title;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    private LocalDateTime updatedAt;

    @JsonProperty("question-ids")
    private List<Long> questionIds;

    @JsonProperty("parent-subject-id")
    private Long parentId;

    @JsonProperty("children-subject-ids")
    private List<Long> childrenIds;
}

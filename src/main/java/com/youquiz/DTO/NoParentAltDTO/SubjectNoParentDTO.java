package com.youquiz.DTO.NoParentAltDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectNoParentDTO {
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> questionIds;

    private Long parentId;

    private List<Long> childrenIds;
}

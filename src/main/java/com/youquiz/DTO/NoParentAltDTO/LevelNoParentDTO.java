package com.youquiz.DTO.NoParentAltDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class LevelNoParentDTO {
    private Long id;

    private String title;

    private String description;

    private double maxPoints;

    private double minPoints;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> questionIds;
}

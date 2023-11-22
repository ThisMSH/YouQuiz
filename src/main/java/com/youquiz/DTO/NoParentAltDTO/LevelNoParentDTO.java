package com.youquiz.DTO.NoParentAltDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("max-points")
    private double maxPoints;

    @JsonProperty("min-points")
    private double minPoints;

    @JsonProperty("created-at")
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    private LocalDateTime updatedAt;

    @JsonProperty("question-ids")
    private List<Long> questionIds;
}

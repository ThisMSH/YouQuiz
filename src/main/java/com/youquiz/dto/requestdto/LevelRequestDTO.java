package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class LevelRequestDTO {
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(min = 2, max = 50, message = "The title must be between 2 and 50 characters.")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters.")
    private String description;

    @NotNull(message = "Max points are required.")
    @DecimalMin(value = "1", message = "Maximum points cannot be lower than 1.")
    @DecimalMax(value = "50.0", message = "Maximum points must be higher than 50.")
    private Double maxPoints;

    @NotNull(message = "Min points are required.")
    @DecimalMin(value = "1", message = "Minimum points cannot be lower than 1.")
    @DecimalMax(value = "50.0", message = "Minimum points must be higher than 50.")
    private Double minPoints;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> questionIds;
}

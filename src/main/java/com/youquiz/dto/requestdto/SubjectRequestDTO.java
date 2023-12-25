package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SubjectRequestDTO {
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title cannot exceed 50 characters.")
    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> questionIds;

    private Long parentId;

    private List<Long> childrenIds;
}

package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MediaRequestDTO {
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title cannot exceed 255 characters.")
    private String title;

    @NotBlank(message = "URL is required.")
    private String url;

    @NotBlank(message = "Media type is required.")
    private String type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Question is required.")
    @Positive(message = "Question ID is invalid.")
    private Long questionId;
}

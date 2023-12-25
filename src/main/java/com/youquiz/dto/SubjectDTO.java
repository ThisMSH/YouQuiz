package com.youquiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title cannot exceed 50 characters.")
    private String title;

    private Long parentId;
}
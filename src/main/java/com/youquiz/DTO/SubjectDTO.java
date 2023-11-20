package com.youquiz.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title cannot exceed 50 characters.")
    private String title;

    @JsonProperty("parent-id")
    private Long parentId;
}

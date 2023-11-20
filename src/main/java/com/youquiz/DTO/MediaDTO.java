package com.youquiz.DTO;

import com.youquiz.Enums.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "The title cannot exceed 255 characters.")
    private String title;

    @NotNull(message = "Media type is required.")
    private MediaType type;

    @NotNull(message = "Media is required.")
    private MultipartFile file;

    @NotNull(message = "Media must be assigned to a question.")
    private Long questionId;
}

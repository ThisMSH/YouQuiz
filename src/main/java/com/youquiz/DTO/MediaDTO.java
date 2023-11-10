package com.youquiz.DTO;

import com.youquiz.Enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private String title;
    private MediaType type;
    private MultipartFile file;
    private Long question_id;
}

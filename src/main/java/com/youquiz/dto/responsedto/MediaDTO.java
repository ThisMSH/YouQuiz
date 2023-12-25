package com.youquiz.dto.responsedto;

import com.youquiz.dto.requestdto.QuestionRequestDTO;
import com.youquiz.enums.MediaType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MediaDTO {
    private Long id;
    private String title;
    private String url;
    private MediaType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private QuestionRequestDTO question;
}

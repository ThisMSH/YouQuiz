package com.youquiz.dto.requestdto;

import com.youquiz.enums.MediaType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MediaNoParentDTO {
    private Long id;

    private String title;

    private String url;

    private MediaType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long questionId;
}

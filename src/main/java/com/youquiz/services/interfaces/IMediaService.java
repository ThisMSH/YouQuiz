package com.youquiz.services.interfaces;

import com.youquiz.dto.requestdto.MediaFileRequestDTO;
import com.youquiz.dto.responsedto.MediaDTO;

import java.util.List;
import java.util.Map;

public interface IMediaService {
    MediaDTO create(MediaFileRequestDTO request);
    MediaDTO delete(Long id);
    MediaDTO get(Long id);
    List<MediaDTO> getByQuestion(Long questionId);
    Map<String, Object> fetchMedia(String str)
}

package com.youquiz.Utils;

import com.youquiz.Exceptions.ResourceBadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

public class Utilities {
    public static Pageable managePagination(int page, int size, String sortBy, String sortOrder) {
        if (!sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) && !sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            throw new ResourceBadRequestException("Please make sure to choose either ascending or descending order.");
        }
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }

    public static MediaType getMediaType(String extension) {
        return switch (extension.toLowerCase()) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "mp4" -> MediaType.valueOf("video/mp4");
            case "webm" -> MediaType.valueOf("video/webm");
            case "ogg" -> MediaType.valueOf("audio/ogg");
            case "mp3" -> MediaType.valueOf("audio/mpeg");
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

}

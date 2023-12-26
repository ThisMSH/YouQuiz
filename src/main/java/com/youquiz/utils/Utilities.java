package com.youquiz.utils;

import com.youquiz.exceptions.ResourceBadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class Utilities<T, C, ID> {
    private final ModelMapper modelMapper;
    private final JpaRepository<C, ID> repository;

    public Utilities(ModelMapper modelMapper, JpaRepository<C, ID> repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    public static Pageable managePagination(Map<String, Object> params) {
        int page = (int) params.get("page");
        int size = (int) params.get("size");
        String sortBy = (String) params.get("sortBy");
        String sortOrder = (String) params.get("sortOrder");

        if (!sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) && !sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            throw new ResourceBadRequestException("Please make sure to choose either ascending or descending order.");
        }
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }

    public Page<T> getAllContents(Map<String, Object> params, Class<T> targetClass) {
        Pageable pageable = managePagination(params);

        Page<C> contents = repository.findAll(pageable);

        return contents.map(content -> modelMapper.map(content, targetClass));
    }

    public static Map<String, Object> params(int page, int size, String sortBy, String sortOrder) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("size", size);
        params.put("sortBy", sortBy);
        params.put("sortOrder", sortOrder);

        return params;
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

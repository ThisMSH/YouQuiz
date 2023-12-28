package com.youquiz.controllers;

import com.youquiz.dto.requestdto.MediaFileRequestDTO;
import com.youquiz.dto.responsedto.MediaDTO;
import com.youquiz.services.MediaService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedia(@PathVariable("id") Long id) {
        MediaDTO media = mediaService.get(id);

        return ResponseHandler.success(
            "The media has been fetched successfully.",
            HttpStatus.OK,
            media
        );
    }

    @GetMapping("/get/{imageName:.+}")
    public ResponseEntity<byte[]> fetchMedia(@PathVariable String imageName) {
        Map<String, Object> mediaObj = mediaService.fetchMedia(imageName);

        return ResponseEntity.ok().contentType((MediaType) mediaObj.get("type")).body((byte[]) mediaObj.get("media"));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createMedia(@ModelAttribute @Valid MediaFileRequestDTO m) {
        MediaDTO media = mediaService.create(m);

        return ResponseHandler.success(
            "The media has been created successfully.",
            HttpStatus.CREATED,
            media
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedia(@PathVariable("id") Long id) {
        MediaDTO deletedMedia = mediaService.delete(id);

        return ResponseHandler.success(
            "The media has been deleted successfully.",
            HttpStatus.OK,
            deletedMedia
        );
    }
}

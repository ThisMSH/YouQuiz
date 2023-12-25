package com.youquiz.controllers;

import com.youquiz.dto.MediaDTO;
import com.youquiz.entities.Media;
import com.youquiz.services.MediaService;
import com.youquiz.utils.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/medias")
public class MediaController {
    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedia(@PathVariable("id") Long id) {
        Media media = mediaService.getMedia(id);

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
    public ResponseEntity<Object> createMedia(@ModelAttribute @Valid MediaDTO m) {
        Media media = mediaService.createMedia(m);

        return ResponseHandler.success(
            "The media has been created successfully.",
            HttpStatus.CREATED,
            media
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedia(@PathVariable("id") Long id) {
        int del = mediaService.deleteMedia(id);

        return ResponseHandler.success(
            "The media has been deleted successfully.",
            HttpStatus.OK,
            del
        );
    }
}

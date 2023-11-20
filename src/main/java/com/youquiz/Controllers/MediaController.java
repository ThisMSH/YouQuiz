package com.youquiz.Controllers;

import com.youquiz.DTO.MediaDTO;
import com.youquiz.Entities.Media;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.StorageException;
import com.youquiz.Exceptions.StorageExpectationFailed;
import com.youquiz.Services.MediaService;
import com.youquiz.Utils.ResponseHandler;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

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

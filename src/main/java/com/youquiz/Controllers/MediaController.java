package com.youquiz.Controllers;

import com.youquiz.DTO.MediaDTO;
import com.youquiz.Entities.Media;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.StorageException;
import com.youquiz.Exceptions.StorageExpectationFailed;
import com.youquiz.Services.MediaService;
import com.youquiz.Utils.ResponseHandler;
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
    public ResponseEntity<Object> createMedia(@ModelAttribute MediaDTO m) {
        Path filePath = null;

        try {
            Media media = new Media();
            Question question = new Question();

            String url = mediaService.saveFile(m.getFile());
            question.setId(m.getQuestionId());

            media.setTitle(m.getTitle());
            media.setType(m.getType());
            media.setUrl(url);
            media.setQuestion(question);

            filePath = Path.of(url);

            media = mediaService.createMedia(media);

            return ResponseHandler.success(
                "The media has been created successfully.",
                HttpStatus.CREATED,
                media
            );
        } catch (Exception e) {
            if (filePath != null) {
                try {
                    FileSystemUtils.deleteRecursively(filePath);
                } catch (IOException ex) {
                    RuntimeException exc = new StorageException("Could not save the media: " + ex.getMessage(), ex);
                    return ResponseHandler.exception(exc, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            RuntimeException exception = new StorageExpectationFailed("Could not save the media: " + e.getMessage(), e);
            return ResponseHandler.exception(exception, HttpStatus.EXPECTATION_FAILED);
        }
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

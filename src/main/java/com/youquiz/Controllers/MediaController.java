package com.youquiz.Controllers;

import com.youquiz.DTO.MediaDTO;
import com.youquiz.Entities.Media;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.StorageException;
import com.youquiz.Services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add")
    public ResponseEntity<String> createMedia(@ModelAttribute MediaDTO m) {
        String message;
        Path filePath = null;

        try {
            Media media = new Media();
            Question question = new Question();

            String url = mediaService.saveFile(m.getFile());
            question.setId(m.getQuestion_id());

            media.setTitle(m.getTitle());
            media.setType(m.getType());
            media.setUrl(url);
            media.setQuestion(question);

            filePath = Path.of(url);

            message = mediaService.createMedia(media);

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            if (filePath != null) {
                try {
                    FileSystemUtils.deleteRecursively(filePath);
                } catch (IOException ex) {
                    throw new StorageException("Failed to delete the saved file after encountering an error during saving the media: " + ex.getMessage());
                }
            }

            message = "Could not save the media: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}

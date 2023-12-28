package com.youquiz.services;

import com.youquiz.dto.requestdto.MediaFileRequestDTO;
import com.youquiz.dto.responsedto.MediaDTO;
import com.youquiz.entities.Media;
import com.youquiz.exceptions.ResourceNotFoundException;
import com.youquiz.exceptions.StorageException;
import com.youquiz.exceptions.StorageExpectationFailed;
import com.youquiz.exceptions.StorageUnprocessableException;
import com.youquiz.repositories.MediaRepository;
import com.youquiz.repositories.QuestionRepository;
import com.youquiz.services.interfaces.IFileStorage;
import com.youquiz.services.interfaces.IMediaService;
import com.youquiz.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MediaService implements IFileStorage, IMediaService {
    private final MediaRepository mediaRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final Path path = Paths.get("storage");

    @Autowired
    public MediaService(MediaRepository mediaRepository, QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.mediaRepository = mediaRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new StorageException("Could not initialize the storage folder.", e);
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageUnprocessableException("Failed to store empty file.");
            }

            String uuid = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = uuid + extension;
            Path filePath = this.path.resolve(newFileName);

            Files.copy(file.getInputStream(), filePath);

            return newFileName;
        } catch (Exception e) {
            throw new StorageException("Failed to save the file: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteOne(String p) {
        Path filePath = Path.of(p);

        try {
            FileSystemUtils.deleteRecursively(filePath);
        } catch (IOException ex) {
            throw new StorageException("Failed to delete the saved file: " + ex.getMessage());
        }
    }

    @Override
    public MediaDTO create(MediaFileRequestDTO request) {
        if (!questionRepository.existsById(request.getQuestionId())) {
            throw new ResourceNotFoundException("Question not found.");
        }

        Path filePath = null;

        try {
            String url = this.saveFile(request.getFile());

            Media media = modelMapper.map(request, Media.class);
            media.setUrl(url);

            filePath = Path.of(url);

            return modelMapper.map(mediaRepository.save(media), MediaDTO.class);
        } catch (Exception e) {
            if (filePath != null) {
                try {
                    FileSystemUtils.deleteRecursively(filePath);
                } catch (IOException ex) {
                    throw new StorageException("Could not delete the save media after encountering an error: " + ex.getMessage(), ex);
                }
            }
            throw new StorageExpectationFailed("Could not save the media: " + e.getMessage(), e);
        }
    }

    @Override
    public MediaDTO delete(Long id) {
        Media media = mediaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Media not found"));

        this.deleteOne(media.getUrl());
        mediaRepository.deleteById(id);

        return modelMapper.map(media, MediaDTO.class);
    }

    @Override
    public MediaDTO get(Long id) {
        Media media = mediaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Media not found."));

        return modelMapper.map(media, MediaDTO.class);
    }

    @Override
    public List<MediaDTO> getByQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found.");
        }

        List<Media> mediaList = mediaRepository.findAllByQuestionId(questionId);

        return mediaList.stream()
            .map(media -> modelMapper.map(media, MediaDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> fetchMedia(String mediaStr) {
        try {
            Path mediaPath = Paths.get("storage/" + mediaStr);
            byte[] mediaBytes = Files.readAllBytes(mediaPath);
            String ext = com.google.common.io.Files.getFileExtension(mediaStr);
            MediaType mediaType = Utilities.getMediaType(ext);

            Map<String, Object> media = new HashMap<>();
            media.put("media", mediaBytes);
            media.put("type", mediaType);

            return media;
        } catch (IOException | InvalidPathException e) {
            throw new StorageException(e.getMessage());
        }
    }
}

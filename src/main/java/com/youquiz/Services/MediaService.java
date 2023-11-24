package com.youquiz.Services;

import com.youquiz.DTO.MediaDTO;
import com.youquiz.Entities.Media;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.ResourceNotFoundException;
import com.youquiz.Exceptions.StorageException;
import com.youquiz.Exceptions.StorageExpectationFailed;
import com.youquiz.Exceptions.StorageUnprocessableException;
import com.youquiz.Repositories.MediaRepository;
import com.youquiz.DAO.FileStorageDAO;
import com.youquiz.Utils.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MediaService implements FileStorageDAO {
    private final MediaRepository mediaRepository;
    private final ModelMapper modelMapper;
    private final Path path = Paths.get("storage");

    @Autowired
    public MediaService(MediaRepository mediaRepository, ModelMapper modelMapper) {
        this.mediaRepository = mediaRepository;
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
            String fullPath = filePath.toString();

            Files.copy(file.getInputStream(), filePath);

            return fullPath;
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

    public Media createMedia(MediaDTO m) {
        Path filePath = null;

        try {
            String url = this.saveFile(m.getFile());

            Media media = modelMapper.map(m, Media.class);
            media.setUrl(url);

            filePath = Path.of(url);

            return mediaRepository.save(media);
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

    public Media getMedia(Long id) {
        Optional<Media> media = mediaRepository.findById(id);

        return media.orElseThrow(() -> new ResourceNotFoundException("Media not found."));
    }

    public Integer deleteMedia(Long id) {
        if (!mediaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Media not found");
        }

        Media media = this.getMedia(id);
        this.deleteOne(media.getUrl());
        mediaRepository.deleteById(id);

        return 1;
    }
}

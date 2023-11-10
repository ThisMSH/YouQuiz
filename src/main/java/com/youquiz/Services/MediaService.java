package com.youquiz.Services;

import com.youquiz.Entities.Media;
import com.youquiz.Entities.Question;
import com.youquiz.Exceptions.StorageException;
import com.youquiz.Repositories.MediaRepository;
import com.youquiz.Utils.FileStorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class MediaService implements FileStorageDAO {
    private final MediaRepository mediaRepository;
    private final Path path = Paths.get("storage");

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
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
                throw new StorageException("Failed to store empty file.");
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
    public Resource loadOne(Long id) {
        return null;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public void deleteOne(Long id) {

    }

    @Override
    public void deleteByQuestion(Question question) {

    }

    public String createMedia(Media media) {
        // Add validation later
        mediaRepository.save(media);
        return "Media has been uploaded successfully.";
    }
}

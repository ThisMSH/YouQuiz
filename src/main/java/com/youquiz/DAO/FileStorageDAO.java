package com.youquiz.DAO;

import com.youquiz.Entities.Question;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageDAO {
    void init();
    String saveFile(MultipartFile file);
    void deleteOne(String p);
}

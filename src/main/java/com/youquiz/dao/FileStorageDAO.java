package com.youquiz.dao;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageDAO {
    void init();
    String saveFile(MultipartFile file);
    void deleteOne(String p);
}

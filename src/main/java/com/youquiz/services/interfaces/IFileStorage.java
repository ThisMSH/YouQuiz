package com.youquiz.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorage {
    void init();
    String saveFile(MultipartFile file);
    void deleteOne(String p);
}

package com.youquiz;

import com.youquiz.dao.FileStorageDAO;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YouQuizApplication implements CommandLineRunner {
    @Resource
    FileStorageDAO storageDAO;

    public static void main(String[] args) {
        SpringApplication.run(YouQuizApplication.class, args);
    }

    @Override
    public void run(String... args) {
        storageDAO.init();
    }
}

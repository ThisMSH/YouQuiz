package com.youquiz.Repositories;

import com.youquiz.Entities.Media;
import com.youquiz.Entities.Question;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByQuestion(Question question);
}

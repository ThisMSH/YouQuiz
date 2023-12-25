package com.youquiz.repositories;

import com.youquiz.entities.Media;
import com.youquiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByQuestion(Question question);
}

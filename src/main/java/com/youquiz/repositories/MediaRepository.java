package com.youquiz.repositories;

import com.youquiz.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllByQuestionId(Long questionId);
}

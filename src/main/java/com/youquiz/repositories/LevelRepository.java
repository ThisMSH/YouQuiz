package com.youquiz.repositories;

import com.youquiz.entities.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Boolean existsByTitleIgnoreCase(String title);
    Page<Level> findAllByTitleLikeIgnoreCase(String title, Pageable pageable);
}

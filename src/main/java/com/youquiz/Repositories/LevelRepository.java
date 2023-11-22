package com.youquiz.Repositories;

import com.youquiz.Entities.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Boolean existsByTitleIgnoreCase(String title);
    Page<Level> findAllByTitleLikeIgnoreCase(String title, Pageable pageable);
}

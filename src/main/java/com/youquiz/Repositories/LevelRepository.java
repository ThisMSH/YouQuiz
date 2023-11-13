package com.youquiz.Repositories;

import com.youquiz.Entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findByTitle(String title);
}

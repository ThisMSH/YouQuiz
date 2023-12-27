package com.youquiz.repositories;

import com.youquiz.entities.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t WHERE LOWER(CONCAT(t.name, ' ', t.familyName)) LIKE %:name%")
    Page<Trainer> findAllByFullName(@Param("name") String fullName, Pageable pageable);
}

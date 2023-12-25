package com.youquiz.repositories;

import com.youquiz.entities.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Page<Subject> findAllByTitleLikeIgnoreCase(String title, Pageable pageable);
}

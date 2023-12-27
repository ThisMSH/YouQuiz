package com.youquiz.repositories;

import com.youquiz.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE LOWER(CONCAT(s.name, ' ', s.familyName)) LIKE %:name%")
    Page<Student> findAllByFullName(@Param("name") String fullName, Pageable pageable);
}

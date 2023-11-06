package com.youquiz.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "subject")
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Subject parent;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "parent")
    private List<Subject> children;
}

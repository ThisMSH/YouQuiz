package com.youquiz.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonProperty("created-at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "subject")
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Subject parent;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "parent")
    @JsonManagedReference
    private List<Subject> children;
}

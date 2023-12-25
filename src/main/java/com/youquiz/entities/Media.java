package com.youquiz.entities;

import com.youquiz.enums.MediaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "The title cannot exceed 255 characters.")
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @NotNull(message = "Media type is required.")
    @Enumerated
    @Column(nullable = false)
    private MediaType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}

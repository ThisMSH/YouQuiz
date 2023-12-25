package com.youquiz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(max = 255, message = "Title cannot exceed 255 characters.")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Duration is required.")
    @Column(nullable = false)
    private short duration;

    @NotNull(message = "Success score is required.")
    @Column(nullable = false)
    private double successScore;

    @Column(nullable = false)
    private boolean canSeeAnswers = false;

    @Column(nullable = false)
    private boolean canSeeResult = false;

    @NotNull(message = "Chances field is required.")
    @Column(nullable = false)
    private byte chances;

    @NotNull(message = "Remark is required.")
    @Column(nullable = false)
    private String remark;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<QuizAssignment> quizAssignments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<QuizQuestion> quizQuestions;
}

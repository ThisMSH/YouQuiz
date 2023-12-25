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
@Table(name = "quiz_assignments")
public class QuizAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Reason is required.")
    @Size(max = 255, message = "Reason field cannot exceed 255 characters.")
    @Column(nullable = false)
    private String reason;

    @NotNull(message = "Starting time is required.")
    @Column(nullable = false)
    private LocalDateTime startingTime;

    @NotNull(message = "Ending time is required.")
    @Column(nullable = false)
    private LocalDateTime endingTime;

    @Column(nullable = false)
    private short attempt = 0;

    @Column(nullable = false)
    private double score = 0;

    @NotNull(message = "Passing score is required.")
    @Column(nullable = false)
    private double passResult;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToMany
    @JoinTable(
        name = "picked_answers",
        joinColumns = @JoinColumn(name = "quiz_assignment_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "answer_validation_id", nullable = false)
    )
    private List<AnswerValidation> answerValidations;
}

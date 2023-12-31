package com.youquiz.entities;

import com.youquiz.enums.QuestionType;
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
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question is required.")
    @Size(max = 255, message = "Question cannot exceed 255 characters.")
    @Column(nullable = false)
    private String question;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private byte answersCount = 0;

    @Column(nullable = false)
    private byte correctAnswersCount = 0;

    @NotNull(message = "Question type is required.")
    @Enumerated
    @Column(nullable = false)
    private QuestionType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Media> medias;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerValidation> answerValidations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<QuizQuestion> quizQuestions;
}

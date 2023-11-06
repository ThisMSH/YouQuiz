package com.youquiz.Entities;

import com.youquiz.Enums.QuestionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private byte answersCount;

    @Column(nullable = false)
    private byte correctAnswersCount;

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
}

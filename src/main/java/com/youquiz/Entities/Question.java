package com.youquiz.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.youquiz.Enums.QuestionType;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonProperty("answers-count")
    private byte answersCount = 0;

    @Column(nullable = false)
    @JsonProperty("correct-answers-count")
    private byte correctAnswersCount = 0;

    @NotNull(message = "Question type is required.")
    @Enumerated
    @Column(nullable = false)
    private QuestionType type;

    @JsonProperty("created-at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
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

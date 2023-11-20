package com.youquiz.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "levels")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(min = 2, max = 50, message = "The title must be between 2 and 50 characters.")
    @Column(nullable = false)
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters.")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Max points is required.")
    @DecimalMax(value = "50.0", message = "Max points must be lower than 50.")
    @JsonProperty("max-points")
    @Column(nullable = false)
    private double maxPoints;

    @NotNull(message = "Min points is required.")
    @DecimalMin(value = "10.0", message = "The points cannot be higher than 10.")
    @JsonProperty("min-points")
    @Column(nullable = false)
    private double minPoints;

    @JsonProperty("created-at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonProperty("updated-at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "level")
    private List<Question> questions;
}

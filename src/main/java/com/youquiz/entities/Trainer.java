package com.youquiz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "trainers")
public class Trainer extends User {
    @NotBlank(message = "Speciality is required.")
    @Size(max = 255, message = "Speciality cannot exceed 255 characters.")
    @Column(nullable = false)
    private String speciality;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer")
    private List<Quiz> quizzes;
}

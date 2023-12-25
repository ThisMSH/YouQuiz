package com.youquiz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {
    @NotNull(message = "Registration date is required.")
    @Column(nullable = false)
    private LocalDate registrationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<QuizAssignment> quizAssignments;
}

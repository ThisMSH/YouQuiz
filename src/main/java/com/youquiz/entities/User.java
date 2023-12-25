package com.youquiz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters.")
    @Column(nullable = false)
    protected String name;

    @NotBlank(message = "Family name is required.")
    @Size(min = 3, max = 100, message = "Family name must be between 3 and 100 characters.")
    @Column(nullable = false)
    protected String familyName;

    @NotBlank(message = "Address is required.")
    @Size(max = 255, message = "Address cannot exceed 255 characters.")
    @Column(nullable = false)
    protected String address;

    @NotNull(message = "Birthdate is required.")
    @Column(nullable = false)
    protected LocalDate birthdate;

    @CreationTimestamp
    protected LocalDate createdAt;

    @UpdateTimestamp
    protected LocalDate updatedAt;
}

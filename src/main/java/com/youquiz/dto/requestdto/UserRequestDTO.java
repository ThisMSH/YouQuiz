package com.youquiz.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class UserRequestDTO {
    protected Long id;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters.")
    protected String name;

    @NotBlank(message = "Family name is required.")
    @Size(min = 3, max = 100, message = "Family name must be between 3 and 100 characters.")
    protected String familyName;

    @NotBlank(message = "Address is required.")
    @Size(max = 255, message = "Address cannot exceed 255 characters.")
    protected String address;

    @NotNull(message = "Birthdate is required.")
    protected LocalDate birthdate;

    protected LocalDate createdAt;

    protected LocalDate updatedAt;
}

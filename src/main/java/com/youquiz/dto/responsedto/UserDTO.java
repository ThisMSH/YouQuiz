package com.youquiz.dto.responsedto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class UserDTO {
    protected Long id;
    protected String name;
    protected String familyName;
    protected String address;
    protected LocalDate birthdate;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}

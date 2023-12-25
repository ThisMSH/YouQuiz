package com.youquiz.dto.responsedto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class UserDTO {
    protected Long id;
    protected String name;
    protected String familyName;
    protected String address;
    protected LocalDate birthdate;
    protected LocalDate createdAt;
    protected LocalDate updatedAt;
}

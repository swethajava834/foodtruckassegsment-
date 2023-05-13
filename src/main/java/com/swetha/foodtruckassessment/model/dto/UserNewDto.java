package com.swetha.foodtruckassessment.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserNewDto {

    @NotBlank(message = "must not be blank")
    @Length(max = 255)
    private String name;

    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank")
    private String email;

    @NotBlank(message = "must not be blank")
    private String password;
}

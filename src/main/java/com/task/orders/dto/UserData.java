package com.task.orders.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @NotNull(message = "Name Should not be null")
    private String name;
    @NotNull
    @Size(min = 5,message = "Password should be between  5 t0 8 characters",max = 16)
    private String password;
    @Email
    private String email;
    @Min(value = 18,message = "Age must be between 18 t0 60")
    @Max(value = 60,message = "Age must be between 18 to 60")
    private int age;
    @NotNull(message = "Gender field should not be null")
    private String gender;
    @NotNull
    @Pattern(regexp = "^\\d{10}$",message = "Invalid Mobile Number")
    private String mobileNumber;

}

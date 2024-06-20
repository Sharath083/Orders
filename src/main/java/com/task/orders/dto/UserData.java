package com.task.orders.dto;

import com.task.orders.constants.Constants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    @NotNull(message = Constants.NAME_SHOULD_NOT_BE_NULL)
    private String name;
    @NotNull
    @Size(min = Constants.FIVE,message = Constants.PASSWORD_SIZE,max = Constants.SIXTEEN)
    private String password;
    @Email
    private String email;
    @Min(value = Constants.EIGHTEEN,message = Constants.AGE_LIMIT)
    @Max(value = Constants.SIXTY,message = Constants.AGE_LIMIT)
    private int age;
    @NotNull(message = Constants.GENDER_MESSAGE)
    private String gender;
    @NotNull
    @Pattern(regexp = Constants.MOBILE_REG,message = Constants.INVALID_MOBILE_NUMBER)
    private String mobileNumber;

}

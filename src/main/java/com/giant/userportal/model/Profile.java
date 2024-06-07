package com.giant.userportal.model;

import com.giant.userportal.validations.UpdateUserGroup;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Profile {

    @NotBlank(message="First Name must not be blank", groups = UpdateUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
            message = "First Name must contain only alphabetic characters and be at most 20 characters long",
            groups = UpdateUserGroup.class)
    private String firstName;

    @NotBlank(message="Father's Name must not be blank", groups = UpdateUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
            message = "Father's Name must contain only alphabetic characters and be at most 20 characters long",
            groups = UpdateUserGroup.class)
    private String fathersName;

    @NotBlank(message="Last Name must not be blank", groups = UpdateUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
            message = "Last Name must contain only alphabetic characters and be at most 20 characters long",
            groups = UpdateUserGroup.class)
    private String lastName;

    @NotBlank(message = "Mobile number must not be blank", groups = UpdateUserGroup.class)
    @Pattern(regexp = "^\\+355(68|69)[0-9]{7}$",
            message = "Mobile number must start with +355 and follow the pattern +355(68/69)XXXXXXX",
            groups = UpdateUserGroup.class)
    private String phoneNumber;

    @NotNull(message = "Birthday must not be null", groups = UpdateUserGroup.class)
    @Past(message = "Birthday must be a past date", groups = UpdateUserGroup.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message="Email must not be blank", groups = UpdateUserGroup.class)
    @Email(message = "Please provide a valid email address", groups = UpdateUserGroup.class)
    private String email;

    private String profileImagePath;
}

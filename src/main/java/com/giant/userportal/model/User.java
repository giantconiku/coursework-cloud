package com.giant.userportal.model;

import com.giant.userportal.annotation.FieldsValueMatch;
import com.giant.userportal.annotation.PasswordValidator;
import com.giant.userportal.validations.CreateUserGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!",
                groups = CreateUserGroup.class
        )
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotBlank(message="First Name must not be blank", groups = CreateUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
             message = "First Name must contain only alphabetic characters and be at most 20 characters long",
             groups = CreateUserGroup.class)
    private String firstName;

    @NotBlank(message="Father's Name must not be blank", groups = CreateUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
            message = "Father's Name must contain only alphabetic characters and be at most 20 characters long",
            groups = CreateUserGroup.class)
    private String fathersName;

    @NotBlank(message="Last Name must not be blank", groups = CreateUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",
             message = "Last Name must contain only alphabetic characters and be at most 20 characters long",
             groups = CreateUserGroup.class)
    private String lastName;

    @NotBlank(message = "Mobile number must not be blank", groups = CreateUserGroup.class)
    @Pattern(regexp = "^\\+355(68|69)[0-9]{7}$",
             message = "Mobile number must start with +355 and follow the pattern +355(68/69)XXXXXXX",
             groups = CreateUserGroup.class)
    private String phoneNumber;

    @NotNull(message = "Birthday must not be null", groups = CreateUserGroup.class)
    @Past(message = "Birthday must be a past date", groups = CreateUserGroup.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message="Email must not be blank", groups = CreateUserGroup.class)
    @Email(message = "Please provide a valid email address", groups = CreateUserGroup.class)
    private String email;

    @NotBlank(message="Password must not be blank", groups = CreateUserGroup.class)
    @PasswordValidator(groups = CreateUserGroup.class)
    private String password;

    @NotBlank(message="Confirm Password must not be blank", groups = CreateUserGroup.class)
    @Transient
    private String confirmPassword;

    private String profileImagePath;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId",nullable = false)
    private Role role;
}

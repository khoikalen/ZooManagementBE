package com.fzoo.zoomanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzoo.zoomanagementsystem.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffAccount {

    @NotBlank(message = "First name should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2,15}$", message = "First name should be word only and from 2 to 15 characters")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2,15}$", message = "Last name should be word only and from 2 to 15 characters")
    private String lastName;

    @NotBlank(message = "Sex should not be empty")
    @Pattern(regexp = "^[a-zA-Z]{2,15}$", message = "Sex should be word only and from 2 to 15 characters")
    private String sex;

    @JsonFormat(pattern = "MM-dd-yyyy")
    @NotNull(message = "Start day should not be empty")
    private LocalDate startDay;

    @Email(message = "Email is invalid")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotBlank(message = "Password should not be empty")
    private String password;

    private Role role;
}

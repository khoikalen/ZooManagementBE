package com.fzoo.zoomanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertRequest {

    private int id;

    @NotBlank(message = "First name should not be empty")
    @Pattern(regexp = "^[a-z A-Z]{2,15}$", message = "First name should be word only and from 2 to 15 characters")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Pattern(regexp = "^[a-z A-Z]{2,15}$", message = "Last name should be word only and from 2 to 15 characters")
    private String lastName;

    @NotBlank(message = "Gender should not be empty")
    @Pattern(regexp = "^[a-z A-Z]{2,15}$", message = "Gender should be word only and from 2 to 15 characters")
    private String gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @NotNull(message = "Start day should not be empty")
    private LocalDate startDay;

    @Email(message = "Email is invalid")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number is invalid")
    private String phoneNumber;

    private String areaName;
}

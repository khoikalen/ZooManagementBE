package com.fzoo.zoomanagementsystem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CageRequest {


    @Pattern(regexp = "^[a-z A-Z]{2,30}$", message = "Cage name should be word only and from 2 to 30 characters")
    private String cageName;

    private byte[] image;

    private String cageStatus;

    private String cageType;

    private String areaName;

    @Email(message = "Email is invalid")
    private String StaffEmail;

}

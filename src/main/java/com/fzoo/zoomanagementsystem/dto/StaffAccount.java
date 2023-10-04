package com.fzoo.zoomanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzoo.zoomanagementsystem.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffAccount {
    private String firstName;

    private String lastName;

    private String sex;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate startDay;

    private String email;

    private String phoneNumber;

    private String password;

    private Role role;
}

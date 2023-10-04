package com.fzoo.zoomanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedStaff {

    private String firstName;

    private String lastName;

    private String sex;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate startDay;

    private String phoneNumber;

}

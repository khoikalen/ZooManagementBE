package com.fzoo.zoomanagementsystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalUpdatingDTO {
    @NotBlank(message = "Animal name should not be empty")
    private String name;

    @NotNull(message = "Animal date of birth not be empty")
    private LocalDate dob;

    @NotNull(message = "Animal date enter zoo should not be empty")
    private LocalDate dez;

    @NotBlank(message = "Animal gender should not be empty")
    private String gender;

}

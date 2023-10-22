package com.fzoo.zoomanagementsystem.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalUpdatingDTO {
    private String name;
    private LocalDate dob;
    private LocalDate dez;
    private String gender;
    private String specie;
    private String status;
    private String cageName;

}

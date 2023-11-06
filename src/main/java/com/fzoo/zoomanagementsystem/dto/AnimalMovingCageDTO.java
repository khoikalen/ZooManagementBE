package com.fzoo.zoomanagementsystem.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalMovingCageDTO {
    private int animalID;
    private String animalSpecie;
    private String animalStatus;
    private int animalCageID;

    private int cageID;
    private String cageName;
    private String cageQuantity;
    private String cageStatus;

}

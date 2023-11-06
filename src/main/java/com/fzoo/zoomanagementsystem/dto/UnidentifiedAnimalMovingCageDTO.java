package com.fzoo.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnidentifiedAnimalMovingCageDTO {
    private int unidentifiedAnimalID;
    private int unidentifiedAnimalQuantity;
    private int unidentifiedAnimalCageID;

    private int cageID;
    private String cageName;
    private String cageQuantity;
    private String cageStatus;

}

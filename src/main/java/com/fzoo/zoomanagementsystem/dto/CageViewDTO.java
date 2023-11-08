package com.fzoo.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CageViewDTO {

    private int id;

    private String name;

    private int quantity;

    private String cageStatus;

    private String cageType;

    private String areaName;

    private String email;
}

package com.fzoo.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CageAreaStaff {

    private String cageName;

    private byte[] image;

    private String cageStatus;

    private String cageType;

    private String areaName;

}

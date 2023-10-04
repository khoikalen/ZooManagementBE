package com.fzoo.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalDTO {
    private int id;
    private String name;
    private String dob;
    private String dez;
    private String sex;
    private String specie;
    private String status;
    private int cage_id;
}

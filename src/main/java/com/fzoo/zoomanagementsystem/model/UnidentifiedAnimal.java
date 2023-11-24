package com.fzoo.zoomanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "unidentified_animal")
public class UnidentifiedAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Unidentified Animal name should not be empty")
    private String name;
    private int status;
    @NotNull(message = "Unidentified Animal quantity should not be empty")
    private int quantity;
    @Column(name = "cage_id")
    private int cageId;
}

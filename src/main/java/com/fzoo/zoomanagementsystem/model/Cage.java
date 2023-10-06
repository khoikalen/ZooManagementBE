package com.fzoo.zoomanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int quantity;

    @Lob
    private byte[] image;

    private String cageStatus;

    private String cageType;

    private int areaId;

    private int staffId;

    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Animal> animal;
}

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

    private String cageStatus;

    private String cageType;

    @Column(name = "area_id")
    private int areaId;

    @Column(name = "staff_id")
    private int staffId;

    @ManyToOne
    @JoinColumn(name = "staff_id",referencedColumnName = "id", updatable = false ,insertable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id", updatable = false ,insertable = false)
    private Area area;

    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Animal> animal;
}

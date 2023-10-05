package com.fzoo.zoomanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private LocalDate dob;

    private LocalDate dez;

    private String sex;

    private String specie;

    private String status;

    @Column(name = "cage_id")
    private int cageId;

    @ManyToOne
    @JoinColumn(name = "cage_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonBackReference
    private Cage cage;
}

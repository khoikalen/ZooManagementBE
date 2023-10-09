package com.fzoo.zoomanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String sex;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate startDay;

    private String email;

    private String phoneNumber;

    @Column(name = "area_id")
    private int areaId;

    @OneToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonBackReference
    private Area area;
}

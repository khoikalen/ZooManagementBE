package com.fzoo.zoomanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;

    private String email;

    private String phoneNumber;

    private int status;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Cage> cage;
}

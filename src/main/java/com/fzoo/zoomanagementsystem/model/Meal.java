package com.fzoo.zoomanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "cage_id")
    private int cageId;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "type")
    private String type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_in_meal",
            joinColumns = @JoinColumn(name = "meal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id")
    )
    @JsonBackReference
    private Set<Food> haveFood;


}

package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findBycageId(int cageId);

    @Query("Select c from Animal c where c.name like %?1% order by c.cageId")
    List<Animal> findByname(String animalName);

}

package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findBycageId(int cageId);
}

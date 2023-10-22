package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.AnimalSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnimalSpeciesRepository extends JpaRepository<AnimalSpecies, Integer> {
    @Query("select c from AnimalSpecies c where c.Name like %?1% order by c.cageId")
    List<AnimalSpecies> findByName(String animalSpecieName);

    @Query("select c from AnimalSpecies c where c.cageId = ?1")
    List<AnimalSpecies> findByCageId(int cageId);
}

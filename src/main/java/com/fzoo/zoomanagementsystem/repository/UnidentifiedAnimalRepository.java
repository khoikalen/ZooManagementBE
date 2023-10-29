package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.UnidentifiedAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface UnidentifiedAnimalRepository extends JpaRepository<UnidentifiedAnimal, Integer> {
    @Query("select c from UnidentifiedAnimal c where c.Name like %?1% order by c.cageId")
    List<UnidentifiedAnimal> findByName(String animalSpecieName);

    @Query("select c from UnidentifiedAnimal c where c.cageId = ?1")
    List<UnidentifiedAnimal> findByCageId(int cageId);


}

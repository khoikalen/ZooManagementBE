package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.UnidentifiedAnimal;
import jakarta.persistence.JoinColumn;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UnidentifiedAnimalRepository extends JpaRepository<UnidentifiedAnimal, Integer> {

    @Query("select c from UnidentifiedAnimal c where c.cageId = ?1")
    List<UnidentifiedAnimal> findByCageId(int cageId);

    @Query("Select u from UnidentifiedAnimal u join Cage c on u.cageId = c.id join Staff s on c.staffId = s.id where s.email = ?1 and u.status = ?2")
    List<UnidentifiedAnimal> findByStaffEmail(String staffEmail, int i);

    UnidentifiedAnimal findAnimalById(int animalID);

    List<UnidentifiedAnimal> findAnimalByCageId(int cageID);

    List<UnidentifiedAnimal> findAllByStatus(Sort cageId, int i);

    UnidentifiedAnimal findByIdAndStatus(int id, int i);

    List<UnidentifiedAnimal> findByNameContainsAndStatus(String animalSpecieName, int i);

    List<UnidentifiedAnimal> findByCageIdAndStatus(int cageID, int i);
}

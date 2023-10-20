package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Cage;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CageRepository extends JpaRepository<Cage, Integer> {

    @Query("SELECT c FROM Cage c WHERE c.staffId = ?1")
    List<Cage> findByStaffId(int staffId);

    @Query("select c from Cage c join Area a on c.areaId = a.id join Expert e on a.id = e.areaId where e.email = ?1")
    List<Cage> findCagesByExpertEmail(String expertEmail);

    Cage findCageById(int cageId);

    Optional<Cage> findByName(String name);

    Cage findCageByName(String cageName);
}

package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.dto.CageViewDTO;
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

    @Query("select c from Cage c join Staff s on c.staffId = s.id where s.email = ?1")
    List<Cage> findCagesByStaffEmail(String staffEmail);

    Cage findCageById(int cageId);

    Optional<Cage> findByName(String name);

    Cage findCageByName(String cageName);

    @Query("SELECT c.id from Cage c where c.name = ?1")
    int findCageIdByCageName(String cageName);

    @Query("select c.name from Cage c where c.id = ?1")
    String findCageNameByCageId(int cageId);

    @Query("select c from Cage c where c.areaId = ?1 and c.cageStatus = 'Empty'")
    List<Cage> findEmptyCageByAreaId(int areaId);
}

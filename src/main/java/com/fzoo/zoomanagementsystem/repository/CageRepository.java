package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Cage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CageRepository extends JpaRepository<Cage, Integer> {

    @Query("SELECT c FROM Cage c WHERE c.staffId = ?1")
    List<Cage> findByStaffId(int staffId);


}

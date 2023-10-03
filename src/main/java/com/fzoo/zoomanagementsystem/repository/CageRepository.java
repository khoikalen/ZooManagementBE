package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Cage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CageRepository extends JpaRepository<Cage, Integer> {
}

package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

    Area findAreaByName(String name);
}

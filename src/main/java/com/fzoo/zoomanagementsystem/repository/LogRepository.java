package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.AnimalLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LogRepository extends JpaRepository<AnimalLog,Integer> {


    List<AnimalLog> findLogByAnimalIdOrderByDateTimeDesc(int id);

    Collection<? extends AnimalLog> findByAnimalIdAndTypeContaining(int id, String type);

}

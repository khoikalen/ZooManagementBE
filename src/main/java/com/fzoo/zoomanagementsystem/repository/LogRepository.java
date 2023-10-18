package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Integer> {


    List<Log> findLogByAnimalIdOrderByDateTimeDesc(int id);
}

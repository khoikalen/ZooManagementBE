package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.UnidentifiedAnimalLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UnidentifiedAnimalLogRepository extends JpaRepository<UnidentifiedAnimalLog,Integer> {
     List<UnidentifiedAnimalLog> findLogByUnidentifiedAnimalIdOrderByDateTimeDesc(int id);

    Collection<? extends UnidentifiedAnimalLog> findByUnidentifiedAnimalIdAndTypeContaining(int id, String type);
}

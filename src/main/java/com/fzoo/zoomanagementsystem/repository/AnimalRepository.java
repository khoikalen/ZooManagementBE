package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Animal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findBycageId(int cageId);

    @Query("Select c from Animal c where c.name like %?1% and c.status != 'Dead' order by c.cageId")
    List<Animal> findByname(String animalName);

    @Query("Select c from Animal c where c.status != 'Dead'")
    List<Animal> findAllAlive(Sort cageId);

    @Query("Select c from Animal c where c.status like 'Dead'")
    List<Animal> findAllDeadAnimal();
    @Query("select c from Animal c where c.status != 'Dead' and c.cageId = ?1")
    Collection<Object> findAllAliveInCage(int cageId);

    @Query("Select c.id from Animal c where c.cageId = :id")
    List<Integer> findIdByCageId(int id);

    @Query("SELECT c from Animal c join Cage d on c.cageId = d.id where d.name like %?1%")
    List<Animal> findByCageName(String cageName);
}

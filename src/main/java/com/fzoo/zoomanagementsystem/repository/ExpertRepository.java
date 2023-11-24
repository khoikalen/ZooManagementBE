package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Expert findExpertById(int expertId);


    Expert findExpertByEmail(String email);

    List<Expert> findByStatus(int i);
}

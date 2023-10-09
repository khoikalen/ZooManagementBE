package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Expert findExpertById(int expertId);


}

package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);



    Staff findStaffById(int staffId);

    @Query("SELECT s FROM Staff s WHERE s.firstName LIKE %?1% OR s.lastName LIKE %?1%")
    List<Staff> findStaffByName(String search);

}

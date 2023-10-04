package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final AccountRepository accountRepository;
    private final CageRepository cageRepository;

    public List<Staff> getAllStaffs() {
        List<Staff> staffPrint = new ArrayList<>();
        List<Staff> staffTmp = staffRepository.findAll();
        for (Staff tmp: staffTmp) {
            if (tmp.getId() != 1) {
                staffPrint.add(tmp);
            }
        }
        return staffPrint;
    }

    @Transactional
    public void deleteStaff(int staffId) {
        boolean exists = staffRepository.existsById(staffId);
        if (exists) {
            setNoOne(staffId);
            deleteStaffAccount(staffId);
            staffRepository.deleteById(staffId);
        } else {
            throw new IllegalStateException("Id" + staffId + "does not exist!!");
        }
    }

    public void setNoOne(int staffId) {
        List<Cage> cages = cageRepository.findByStaffId(staffId);
        for (Cage cage : cages) {
            cage.setStaffId(1);
        }
    }

    public void deleteStaffAccount(int staffId) {
        Optional<Staff> staff = staffRepository.findById(staffId);
        String email = null;
        if (staff.isPresent()) {
            email = staff.get().getEmail();
        } else {
            throw new IllegalStateException("Staff with email " + email + "does not exist!!!");
        }
        accountRepository.deleteAccountByEmail(email);
    }


    public void updateStaff(int staffId, String firstName, String lastName, String sex, LocalDate startDay, String phoneNumber) {
        Staff staff = staffRepository.findStaffById(staffId).orElseThrow(() -> new IllegalStateException("Staff with email " + staffId + "does not exist!"));
        if (firstName!= null) {
            staff.setFirstName(firstName);
        }
        if (lastName != null) {
            staff.setLastName(lastName);
        }
        if (sex != null) {
            staff.setSex(sex);
        }
        if (startDay != null) {
            staff.setStartDay(startDay);
        }
        if (phoneNumber != null) {
            staff.setPhoneNumber(phoneNumber);
        }
        staffRepository.save(staff);
    }

    public List<Staff> searchStaffbyName(String name) {
        return staffRepository.findStaffByName(name);
    }
}

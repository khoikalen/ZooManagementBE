package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final AccountRepository accountRepository;
    private final CageRepository cageRepository;
    private final ExpertRepository expertRepository;

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
            throw new IllegalStateException("Id " + staffId + " does not exist!!");
        }
    }

    public void setNoOne(int staffId) {
        List<Cage> cages = cageRepository.findByStaffId(staffId);
        cages.forEach(cage -> cage.setStaffId(1));
    }

    public void deleteStaffAccount(int staffId) {
        Optional<Staff> staff = staffRepository.findById(staffId);
        String email = null;
        if (staff.isPresent()) {
            email = staff.get().getEmail();
        } else {
            throw new IllegalStateException("Staff with id " + staffId + " does not exist!!!");
        }
        accountRepository.deleteAccountByEmail(email);
    }


    public void updateStaff(int staffId, String firstName, String lastName, String sex, Date startDay, String phoneNumber) {
        Staff staff = staffRepository.findStaffById(staffId);
        boolean checkPhoneNumberInStaff = staffRepository.existsByPhoneNumber(phoneNumber);
        boolean checkPhoneNumberInExpert = expertRepository.existsByPhoneNumber(phoneNumber);
        if (staff != null) {
            if ((!checkPhoneNumberInStaff && !checkPhoneNumberInExpert) || staff.getPhoneNumber().equals(phoneNumber) ) {
                staff.setFirstName(firstName);
                staff.setLastName(lastName);
                staff.setSex(sex);
                staff.setStartDay(startDay);
                staff.setPhoneNumber(phoneNumber);
                staffRepository.save(staff);
            } else {
                throw new IllegalStateException("Phone number " + phoneNumber + " is already existed");
            }
        } else {
            throw  new IllegalStateException("Staff with email " + staffId + " does not exist!");
        }
    }

    public List<Staff> searchStaffbyName(String name) {
        return staffRepository.findStaffByName(name);
    }

    public Staff getStaffById(int staffId) {
        return staffRepository.findStaffById(staffId);
    }
}

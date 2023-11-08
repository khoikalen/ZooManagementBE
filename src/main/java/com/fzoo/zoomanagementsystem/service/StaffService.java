package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Account;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.RefreshToken;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final AccountRepository accountRepository;
    private final CageRepository cageRepository;
    private final ExpertRepository expertRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public List<Staff> getAllStaffs() {
        List<Staff> staffPrint = new ArrayList<>();
        List<Staff> staffTmp = staffRepository.findAll();
        for (Staff tmp: staffTmp) {
            if (tmp.getStatus() == 1) {
                staffPrint.add(tmp);
            }
        }
        return staffPrint;
    }

    @Transactional
    public void deleteStaff(int staffId) {
        boolean exists = staffRepository.existsById(staffId);
        if (exists) {
            Staff staffToDelete = staffRepository.findStaffById(staffId);
            setNoOne(staffId);
            deleteStaffAccount(staffId);
            staffToDelete.setStatus(0);
            staffRepository.save(staffToDelete);
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
        String email;
        if (staff.isPresent()) {
            email = staff.get().getEmail();
        } else {
            throw new IllegalStateException("Staff with id " + staffId + " does not exist!!!");
        }
        Account account = accountRepository.findAccountByEmail(email);
        RefreshToken refreshToken = refreshTokenRepository.findByAccountInfo_Id(account.getId());
        if (refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
        }
        accountRepository.deleteAccountByEmail(email);
    }


    public void updateStaff(int staffId, String firstName, String lastName, String sex, Date startDay, String phoneNumber) {
        Staff staff = staffRepository.findStaffById(staffId);
        if (staff.getStatus() == 1) {
        boolean checkPhoneNumberInStaff = staffRepository.existsByPhoneNumber(phoneNumber);
        boolean checkPhoneNumberInExpert = expertRepository.existsByPhoneNumber(phoneNumber);
        if (staff != null) {
            if ((!checkPhoneNumberInStaff && !checkPhoneNumberInExpert) || staff.getPhoneNumber().equals(phoneNumber)) {
                staff.setFirstName(firstName);
                staff.setLastName(lastName);
                staff.setGender(sex);
                staff.setStartDay(startDay);
                staff.setPhoneNumber(phoneNumber);
                staffRepository.save(staff);
            } else {
                throw new IllegalStateException("Phone number " + phoneNumber + " is already existed");
            }
        } else {
            throw new IllegalStateException("Staff with ID " + staffId + " does not exist!");
        }
        } else {
            throw new IllegalStateException("This staff was no longer existed");
        }
    }

    public List<Staff> searchStaffbyName(String name) {
        List<Staff> listSearch = staffRepository.findStaffByName(name);
        List<Staff> listShow = new ArrayList<>();
        for(Staff tmp: listSearch) {
            if(tmp.getStatus() != 0) {
                listShow.add(tmp);
            }
        }
        return listShow;
    }

    public Staff getStaffById(int staffId) {
        Staff staff = staffRepository.findStaffById(staffId);
        if (staff.getStatus() == 0) {
            throw new IllegalStateException("This staff was no longer existed");
        }
        return staff;
    }
}

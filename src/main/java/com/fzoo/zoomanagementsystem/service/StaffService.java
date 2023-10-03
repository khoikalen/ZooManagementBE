package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

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
}

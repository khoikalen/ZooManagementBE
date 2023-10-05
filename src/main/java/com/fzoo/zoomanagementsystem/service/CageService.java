package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.CageAreaStaff;
import com.fzoo.zoomanagementsystem.model.Area;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.AreaRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CageService {

    private final CageRepository cageRepository;
    private final AreaRepository areaRepository;
    private final StaffRepository staffRepository;

    public List<Cage> getAllCages() {
        return cageRepository.findAll(Sort.by(Sort.Direction.ASC, "areaId"));
    }

    public void addNewCage(CageAreaStaff request) {
        Area area = areaRepository.findAreaByName(request.getAreaName());
        Optional<Staff> staff = staffRepository.findStaffById(1);
        Cage cage = new Cage();
        cage.setName(request.getCageName());
        cage.setQuantity(0);
        cage.setImage(request.getImage());
        cage.setCageStatus(request.getCageStatus());
        cage.setCageType(request.getCageType());
        cage.setAreaId(area.getId());
        cage.setStaffId(staff.get().getId());
        cage.setArea(area);
        cage.setStaff(staff.get());
        cageRepository.save(cage);
    }
}
package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.CageRequest;
import com.fzoo.zoomanagementsystem.dto.CageViewDTO;
import com.fzoo.zoomanagementsystem.exception.UserNotFoundException;
import com.fzoo.zoomanagementsystem.model.Area;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.AreaRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CageService {

    private final CageRepository cageRepository;
    private final AreaRepository areaRepository;
    private final StaffRepository staffRepository;

    public List<CageViewDTO> getAllCages() {
        List<Cage> cageList = cageRepository.findAll(Sort.by(Sort.Direction.ASC, "areaId"));
        List<CageViewDTO> listCageView = new ArrayList<>();
        for (Cage cage : cageList) {
            listCageView.add(new CageViewDTO(
                    cage.getId(),
                    cage.getName(),
                    cage.getQuantity(),
                    cage.getCageStatus(),
                    cage.getCageType(),
                    cage.getArea().getName(),
                    cage.getStaff().getEmail()));
        }
        return listCageView;
    }

    public void addNewCage(CageRequest request) throws UserNotFoundException {
        Area area = areaRepository.findAreaByName(request.getAreaName());
        Staff staff;
        if (request.getStaffEmail().isBlank()) {
            staff = staffRepository.findStaffById(1);
        } else {
            staff = staffRepository.findStaffByEmail(request.getStaffEmail());
            if (staff == null) {
                throw new UserNotFoundException("Staff with email " + request.getStaffEmail() + " does not exist!");
            }
        }
        Cage cage = new Cage();
        cage.setName(request.getCageName());
        cage.setQuantity(0);
        cage.setImage(request.getImage());
        cage.setCageStatus(request.getCageStatus());
        cage.setCageType(request.getCageType());
        cage.setAreaId(area.getId());
        cage.setStaffId(staff.getId());
        cage.setArea(area);
        cage.setStaff(staff);
        cageRepository.save(cage);
    }

    public Cage getCageById(int cageId) {
        return cageRepository.findCageById(cageId);
    }


}
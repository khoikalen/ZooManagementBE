package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.CageRequest;
import com.fzoo.zoomanagementsystem.dto.CageViewDTO;
import com.fzoo.zoomanagementsystem.exception.UserNotFoundException;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CageService {

    private final CageRepository cageRepository;
    private final AreaRepository areaRepository;
    private final StaffRepository staffRepository;
    private final ExpertRepository expertRepository;
    private final AnimalRepository animalRepository;
    private final UnidentifiedAnimalRepository unidentifiedAnimalRepository;

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
        if (request.getCageStatus().equals("Empty")) {
            cage.setName("Empty cage");
        } else {
            cage.setName(request.getCageName());
        }
        cage.setQuantity(0);
        cage.setCageStatus(request.getCageStatus());
        cage.setCageType(request.getCageType());
        cage.setAreaId(area.getId());
        cage.setStaffId(staff.getId());
        cage.setArea(area);
        cage.setStaff(staff);
        cageRepository.save(cage);
    }

    public Cage getCageById(int cageId) throws UserNotFoundException {
        Cage cage = cageRepository.findCageById(cageId);
        if (cage == null) {
            throw new UserNotFoundException("Cage with id " + cageId + " does not exist");
        }
        return cage;
    }


    public void updateCage(int cageId, CageRequest request) throws UserNotFoundException {
        Cage cage = cageRepository.findCageById(cageId);
        if (request.getCageStatus().equals("Empty")) {
            List<Animal> animalList = animalRepository.findBycageId(cageId);
            if (!animalList.isEmpty()) {
                throw new IllegalStateException("Can not update Cage Status to empty because there are animals in cage");
            } else {
                List<UnidentifiedAnimal> animalSpecieList = unidentifiedAnimalRepository.findByCageId(cageId);
                if (!animalSpecieList.isEmpty()) {
                    throw new IllegalStateException("Can not update Cage Status to empty because there are animals in cage");
                }
            }

        }
        Staff staff;
        if (request.getStaffEmail().isBlank()) {
            staff = staffRepository.findStaffById(1);
        } else {
            staff = staffRepository.findStaffByEmail(request.getStaffEmail());
            if (staff == null) {
                throw new UserNotFoundException("Staff with email " + request.getStaffEmail() + " does not exist!");
            }
        }
        cage.setName(request.getCageName());
        cage.setCageStatus(request.getCageStatus());
        cage.setStaff(staff);
        cage.setStaffId(staff.getId());
        cageRepository.save(cage);
    }

    public void deleteCage(int cageId) {
        Cage cage = cageRepository.findCageById(cageId);
        if (cage.getQuantity() == 0) {
            cageRepository.deleteById(cageId);
        } else {
            throw new IllegalStateException("Can not delete Cage because there are animals in cage");
        }
    }


    public List<CageViewDTO> getCagesByStaffEmail(String staffEmail) {
        List<Cage> cageList = cageRepository.findCagesByStaffEmail(staffEmail);
        List<CageViewDTO> cageListView = new ArrayList<>();
        for (Cage cage : cageList) {
            cageListView.add(new CageViewDTO(
                    cage.getId(),
                    cage.getName(),
                    cage.getQuantity(),
                    cage.getCageStatus(),
                    cage.getCageType(),
                    cage.getArea().getName(),
                    cage.getStaff().getEmail()
            ));
        }
        return cageListView;
    }

    public List<CageViewDTO> getEmptyCageByAreaId(int animalId) {
        Optional<Animal> animal = animalRepository.findById(animalId);
        Cage cage = cageRepository.findCageById(animal.get().getCageId());
        List<Cage> EmptyCage = cageRepository.findEmptyCageByAreaId(cage.getAreaId());
        List<CageViewDTO> cageListView = new ArrayList<>();
        for (Cage cageCheck : EmptyCage) {
            cageListView.add(new CageViewDTO(
                    cageCheck.getId(),
                    cageCheck.getName(),
                    cageCheck.getQuantity(),
                    cageCheck.getCageStatus(),
                    cageCheck.getCageType(),
                    cageCheck.getArea().getName(),
                    cageCheck.getStaff().getEmail()
            ));
        }
        return cageListView;
    }


    public Page<Cage> getCagesByExpertEmail(String expertEmail) {
        int page = 0;
        List<Expert> experts = expertRepository.findByStatus(1);
        for (int i = 0; i < experts.size(); i++) {
            if (experts.get(i).getEmail().equals(expertEmail)) {
                page = i;
            }
        }
        double result = (double)cageRepository.countByCageStatusLike("Owned") / experts.size();
        int pageSize = (int) Math.ceil(result);
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Cage> cage = cageRepository.findAll(pageable);
        return cage;

    }

}
package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.AnimalMovingCageDTO;
import com.fzoo.zoomanagementsystem.dto.UnidentifiedAnimalMovingCageDTO;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.UnidentifiedAnimalLogRepository;
import com.fzoo.zoomanagementsystem.repository.UnidentifiedAnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UnidentifiedAnimalService {
    private final UnidentifiedAnimalRepository unidentifiedAnimalRepository;
    private final CageRepository cageRepository;
    private final UnidentifiedAnimalLogRepository unidentifiedAnimalLogRepository;
    public List<UnidentifiedAnimal> getAllAnimalSpecies() {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findAll(Sort.by(Sort.Direction.ASC, "cageId"));
        if (unidentifiedAnimalList.isEmpty()) throw new IllegalStateException("There are no Animal Species");
        return unidentifiedAnimalList;
    }

    public UnidentifiedAnimal getAnimalSpeciesByID(int id) {
        return unidentifiedAnimalRepository.findById(id).orElseThrow(() -> new IllegalStateException("No result for Animal Species searching!"));
    }

    public List<UnidentifiedAnimal> getAnimalSpeciesByName(String animalSpecieName) {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByNameContains(animalSpecieName);
        if (unidentifiedAnimalList.isEmpty()) throw new IllegalStateException("No result for Animal Species searching");
        return unidentifiedAnimalList;
    }

    public List<UnidentifiedAnimal> getAnimalSpeciesByCageID(int cageID) {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByCageId(cageID);
        if (unidentifiedAnimalList.isEmpty()) throw new IllegalStateException("No result for Animal Species searching");
        return unidentifiedAnimalList;
    }

    public void updateCageQuantity(int cageID) {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        int cageQuantity = 0;
        for (UnidentifiedAnimal unidentifiedAnimal : unidentifiedAnimalList) {
            cageQuantity += unidentifiedAnimal.getQuantity();
        }
        cage.setQuantity(cageQuantity);
        cageRepository.save(cage);
    }

    public void UpdateAnimalSpecies(int animalSpecieID, UnidentifiedAnimal request) {
        UnidentifiedAnimal unidentifiedAnimal = unidentifiedAnimalRepository.findById(animalSpecieID).orElseThrow(() -> new IllegalStateException("Animal specie with " + animalSpecieID + " is not found"));
        if (request.getName() != null) unidentifiedAnimal.setName(request.getName());
        unidentifiedAnimal.setQuantity(request.getQuantity());
        unidentifiedAnimal.setCageId(request.getCageId());
        unidentifiedAnimalRepository.save(unidentifiedAnimal);
        updateCageQuantity(unidentifiedAnimal.getCageId());
    }

    public void CreateAnimalSpecies(UnidentifiedAnimal unidentifiedAnimal, int cageID) {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        boolean isDuplicated = false;
        if (cage == null) throw new IllegalStateException("There some mismatch in finding cage!");
        if (cage.getCageType().equalsIgnoreCase("Open")) {
            unidentifiedAnimal.setCageId(cageID);
            if (!unidentifiedAnimalList.isEmpty()) {
                for (UnidentifiedAnimal species : unidentifiedAnimalList) {
                    if (species.getName().equalsIgnoreCase(unidentifiedAnimal.getName())) {
                        species.setQuantity(species.getQuantity() + unidentifiedAnimal.getQuantity());
                        unidentifiedAnimalRepository.save(species);
                        isDuplicated = true;
                    }
                }
            }
            if (!isDuplicated) {
                unidentifiedAnimalRepository.save(unidentifiedAnimal);
            }
            updateCageQuantity(unidentifiedAnimal.getCageId());
        } else throw new IllegalStateException("This type of animal belong to Open cage!");
    }

    public void deleteAnimalSpecies(int unidentifiedAnimalID) {
        UnidentifiedAnimal unidentifiedAnimal = unidentifiedAnimalRepository.findAnimalById(unidentifiedAnimalID);
        if(unidentifiedAnimal == null) throw new IllegalStateException("Can not find this animal!");
        unidentifiedAnimalRepository.delete(unidentifiedAnimal);
        updateCageQuantity(unidentifiedAnimal.getCageId());
    }

    public List<UnidentifiedAnimal> searchUnidentifiedAnimalByStaffEmail(String staffEmail){
        List<UnidentifiedAnimal> animalList = unidentifiedAnimalRepository.findByStaffEmail(staffEmail);
        if(animalList.isEmpty()) throw new IllegalStateException("There are no animals under this staff control");
        return animalList;
    }

    public void UpdateCageQuantity(int cageID) {
        List<UnidentifiedAnimal> animalList = unidentifiedAnimalRepository.findAnimalByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        int totalQuantity = 0;
        for(UnidentifiedAnimal animal : animalList){
            totalQuantity += animal.getQuantity();
        }
        cage.setQuantity(totalQuantity);
        cageRepository.save(cage);
    }

    public void createUnidentifiedAnimalMoveCageLog(UnidentifiedAnimal animal, Cage cageMoveTo, Cage animalCage) {
        unidentifiedAnimalLogRepository.save(new UnidentifiedAnimalLog(0, "Move cage", LocalDateTime.now(),
                "Move species '" + animal.getName() + "' from cage '" +
                        animalCage.getName() + "' to cage '" + cageMoveTo.getName() + "'", animal.getId()));
    }
    public void moveCageUnidentifiedAnimal(int animalID, UnidentifiedAnimalMovingCageDTO request) {
        UnidentifiedAnimal animal = unidentifiedAnimalRepository.findAnimalById(animalID); //Animal need to move
        Cage animalCage = cageRepository.findCageById(animal.getCageId()); //Current cage of animal
        Cage cageMoveTo = cageRepository.findCageById(request.getCageID()); //Cage need to move animal to

        if (cageMoveTo.getCageType().equalsIgnoreCase("Open")) {
            animal.setCageId(cageMoveTo.getId());
            if (cageMoveTo.getQuantity() == 0) {
                cageMoveTo.setCageStatus("Owned");
                cageMoveTo.setName(request.getCageName());
            }
            unidentifiedAnimalRepository.save(animal);
            UpdateCageQuantity(cageMoveTo.getId());
            UpdateCageQuantity(animalCage.getId());
            if(!animalCage.equals(cageMoveTo)){
                createUnidentifiedAnimalMoveCageLog(animal, cageMoveTo, animalCage);
            }
        } else throw new IllegalStateException("Can not move this animal to 'Close' cage!");
    }
}

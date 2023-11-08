package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.AnimalMovingCageDTO;
import com.fzoo.zoomanagementsystem.dto.UnidentifiedAnimalMovingCageDTO;
import com.fzoo.zoomanagementsystem.exception.EmptyStringException;
import com.fzoo.zoomanagementsystem.exception.MultipleExceptions;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.UnidentifiedAnimalLogRepository;
import com.fzoo.zoomanagementsystem.repository.UnidentifiedAnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UnidentifiedAnimalService {
    private final UnidentifiedAnimalRepository unidentifiedAnimalRepository;
    private final CageRepository cageRepository;
    private final UnidentifiedAnimalLogRepository unidentifiedAnimalLogRepository;
    private final ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");

    public List<UnidentifiedAnimal> getAllAnimalSpecies() {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findAllByStatus(Sort.by(Sort.Direction.ASC, "cageId"), 1);
        if (unidentifiedAnimalList.isEmpty()) throw new IllegalStateException("There are no Animal Species");
        return unidentifiedAnimalList;
    }

    public UnidentifiedAnimal getAnimalSpeciesByID(int id) {
        UnidentifiedAnimal unidentifiedAnimal = unidentifiedAnimalRepository.findByIdAndStatus(id, 1);
        if(unidentifiedAnimal == null) throw new IllegalStateException("No result for Animal Species searching!");
        return unidentifiedAnimal;
    }

    public List<UnidentifiedAnimal> getAnimalSpeciesByName(String animalSpecieName) {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByNameContainsAndStatus(animalSpecieName, 1);
        if (unidentifiedAnimalList.isEmpty()) throw new IllegalStateException("No result for Animal Species searching");
        return unidentifiedAnimalList;
    }

    public List<UnidentifiedAnimal> getAnimalSpeciesByCageID(int cageID) {
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByCageIdAndStatus(cageID, 1);
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

    public String validateString(String stringValue) {
        return stringValue.replaceAll("\\s{2,}", " ");
    }
    public void validateUnidentifiedAnimal(UnidentifiedAnimal animal){
        animal.setName(animal.getName().trim());
        animal.setName(validateString(animal.getName()));
        if(animal.getQuantity() < 0) throw new NumberFormatException("Quantity can not be negative");
    }
    public void UpdateAnimalSpecies(int animalSpecieID, UnidentifiedAnimal request) {
        validateUnidentifiedAnimal(request);
        UnidentifiedAnimal unidentifiedAnimal = unidentifiedAnimalRepository.findById(animalSpecieID).orElseThrow(() -> new IllegalStateException("Animal specie with " + animalSpecieID + " is not found"));
        if (request.getName() != null) unidentifiedAnimal.setName(request.getName());

        unidentifiedAnimal.setQuantity(request.getQuantity());
        if (unidentifiedAnimal.getQuantity() == 0) unidentifiedAnimal.setStatus(0);
        else unidentifiedAnimal.setStatus(1);
        unidentifiedAnimal.setCageId(unidentifiedAnimal.getCageId());
        unidentifiedAnimalRepository.save(unidentifiedAnimal);
        updateCageQuantity(unidentifiedAnimal.getCageId());
    }

    public void CreateAnimalSpecies(UnidentifiedAnimal unidentifiedAnimal, int cageID) {
        validateUnidentifiedAnimal(unidentifiedAnimal);
        List<UnidentifiedAnimal> unidentifiedAnimalList = unidentifiedAnimalRepository.findByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        boolean isDuplicated = false;
        if (cage == null) throw new IllegalStateException("There some mismatch in finding cage!");
        if (cage.getStatus() == 0)
            throw new IllegalStateException("This cage is not ready to use, please contact admin!");
        if (cage.getCageType().equalsIgnoreCase("Open")) {
            unidentifiedAnimal.setCageId(cageID);

            if (!unidentifiedAnimalList.isEmpty()) {
                for (UnidentifiedAnimal species : unidentifiedAnimalList) {
                    if (species.getName().equalsIgnoreCase(unidentifiedAnimal.getName())) {
                        species.setQuantity(species.getQuantity() + unidentifiedAnimal.getQuantity());
                        if(species.getQuantity() > 0) species.setStatus(1);
                        else species.setStatus(0);
                        unidentifiedAnimalRepository.save(species);
                        isDuplicated = true;
                    }
                }
            }
            if (!isDuplicated) {
                if(unidentifiedAnimal.getQuantity() > 0) unidentifiedAnimal.setStatus(1);
                else unidentifiedAnimal.setStatus(0);
                unidentifiedAnimalRepository.save(unidentifiedAnimal);
            }
            updateCageQuantity(unidentifiedAnimal.getCageId());
        } else throw new IllegalStateException("This type of animal belong to Open cage!");
    }

    public void deleteAnimalSpecies(int unidentifiedAnimalID) {
        UnidentifiedAnimal unidentifiedAnimal = unidentifiedAnimalRepository.findAnimalById(unidentifiedAnimalID);
        if (unidentifiedAnimal == null) throw new IllegalStateException("Can not find this animal!");
        unidentifiedAnimal.setStatus(0);
        unidentifiedAnimal.setQuantity(0);
        unidentifiedAnimalRepository.save(unidentifiedAnimal);
        updateCageQuantity(unidentifiedAnimal.getCageId());
    }

    public List<UnidentifiedAnimal> searchUnidentifiedAnimalByStaffEmail(String staffEmail) {
        List<UnidentifiedAnimal> animalList = unidentifiedAnimalRepository.findByStaffEmail(staffEmail, 1);
        if (animalList.isEmpty()) throw new IllegalStateException("There are no animals under this staff control");
        return animalList;
    }

    public void UpdateCageQuantity(int cageID) {
        List<UnidentifiedAnimal> animalList = unidentifiedAnimalRepository.findAnimalByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        int totalQuantity = 0;
        for (UnidentifiedAnimal animal : animalList) {
            totalQuantity += animal.getQuantity();
        }
        cage.setQuantity(totalQuantity);
        cageRepository.save(cage);
    }

    public void createUnidentifiedAnimalMoveCageLog(UnidentifiedAnimal animal, Cage cageMoveTo, Cage animalCage) {
        unidentifiedAnimalLogRepository.save(new UnidentifiedAnimalLog(0, "Move cage", LocalDateTime.now(zone),
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
            if (!animalCage.equals(cageMoveTo)) {
                createUnidentifiedAnimalMoveCageLog(animal, cageMoveTo, animalCage);
            }
        } else throw new IllegalStateException("Can not move this animal to 'Close' cage!");
    }
}

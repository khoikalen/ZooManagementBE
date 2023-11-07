package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.AnimalMovingCageDTO;
import com.fzoo.zoomanagementsystem.dto.AnimalUpdatingDTO;
import com.fzoo.zoomanagementsystem.exception.EmptyStringException;
import com.fzoo.zoomanagementsystem.exception.MultipleExceptions;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.AnimalLog;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.UnidentifiedAnimal;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.LogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final CageRepository cageRepository;
    private final LogRepository logRepository;
    private final ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");

    public List<Animal> getAllAnimals() {
        List<Animal> animalList = animalRepository.findAllAlive(Sort.by(Sort.Direction.ASC, "cageId"));
        if (animalList.isEmpty()) throw new IllegalStateException("There are no animals");
        return animalList;
    }

    public List<Animal> getAllDeadAnimal() {
        List<Animal> animalList = animalRepository.findAllDeadAnimal();
        if (animalList.isEmpty()) throw new IllegalStateException("There are no animals");
        return animalList;
    }

    public Animal searchAnimalByID(int animalID) {
        Animal animal = animalRepository.findById(animalID).orElseThrow(() -> new IllegalStateException("This animal is not exists!"));
        return animal;
    }

    public List<Animal> searchAnimalByName(String animalName) {
        List<Animal> animalList = animalRepository.findByname(animalName);
        if (animalList.isEmpty()) throw new IllegalStateException("There are no animals");
        return animalList;
    }

    public String validateString(String stringValue) {
        return stringValue.replaceAll("\\s{2,}", " ");
    }

    public void validateAnimalInput(Animal animal) throws EmptyStringException {
        List<RuntimeException> exceptions = new ArrayList<>();
        animal.setName(animal.getName().trim());
        animal.setName(validateString(animal.getName()));

        animal.setGender(animal.getGender().trim().toLowerCase());
        animal.setGender(validateString(animal.getGender()));

        animal.setSpecie(animal.getSpecie().trim());
        animal.setSpecie(validateString(animal.getSpecie()));

        animal.setStatus(animal.getStatus().trim());
        animal.setStatus(validateString(animal.getStatus()));

        if (animal.getName().isBlank()) exceptions.add(new EmptyStringException("Can not let animal name null!"));
        if(animal.getDob() == null) exceptions.add(new EmptyStringException("Can not let animal DOB null!"));
        else if (animal.getDob().isAfter(LocalDate.now()))
            exceptions.add(new IllegalStateException("Date of Birth exceeds current date!"));
        if (animal.getGender().isBlank()) exceptions.add(new EmptyStringException("Can not let animal gender null!"));
        else if (!animal.getGender().equalsIgnoreCase("male") && !animal.getGender().equalsIgnoreCase("female"))
            exceptions.add(new IllegalStateException("There are only 2 types of gender: male / female"));
        if (animal.getSpecie().isBlank()) exceptions.add(new EmptyStringException("Can not let animal specie null!"));
        if (animal.getStatus().isBlank()) exceptions.add(new EmptyStringException("Can not let animal status null!"));
        if (!exceptions.isEmpty()) throw new MultipleExceptions(exceptions);
    }

    public void createNewAnimal(Animal animal, int cageID) throws EmptyStringException {
        validateAnimalInput(animal);
        Cage cage = cageRepository.findCageById(cageID);
        if (cage == null) throw new IllegalStateException("There some mismatch in finding cage!");
        int cageQuantity = 0;
        animal.setDez(LocalDate.now());
        Animal animalExistedInCage = animalRepository.findFirstAnimalByCageId(cageID);
        if (cage.getCageType().equalsIgnoreCase("Close")) {
            animal.setCageId(cageID);
            if (animalExistedInCage == null) {
                animalRepository.save(animal);
                cageQuantity++;
            } else if (animal.getSpecie().equalsIgnoreCase(animalExistedInCage.getSpecie())) {
                animalRepository.save(animal);
                for (Animal animalInCage : animalRepository.findBycageId(cageID)) {
                    if (!animalInCage.getStatus().equalsIgnoreCase("Dead")) {
                        cageQuantity++;
                    }
                }
            } else throw new IllegalStateException("Can not create this type of animal because difference in specie");
        } else throw new IllegalStateException("This animal is belong to 'Close' cage!");
        cage.setQuantity(cageQuantity);
        cageRepository.save(cage);
    }

    public void validateAnimalUpdate(AnimalUpdatingDTO animal) throws EmptyStringException {
        List<RuntimeException> exceptions = new ArrayList<>();
        animal.setName(animal.getName().trim());
        animal.setName(validateString(animal.getName()));

        animal.setGender(animal.getGender().trim().toLowerCase());
        animal.setGender(validateString(animal.getGender()));


        if (animal.getName().isBlank()) exceptions.add(new EmptyStringException("Can not let animal name null!"));
        if(animal.getDob() == null) exceptions.add(new EmptyStringException("Can not let animal Date of birth null!"));
        else if (animal.getDob().isAfter(LocalDate.now()))
            exceptions.add(new IllegalStateException("Date of birth exceeds current date!"));
        if(animal.getDez() == null) exceptions.add(new EmptyStringException("Can not let animal Date enter zoo null!"));
        else if (animal.getDez().isAfter(LocalDate.now()))
            exceptions.add(new IllegalStateException("Date enter zoo exceeds current date!"));
        if (animal.getGender().isBlank()) exceptions.add(new EmptyStringException("Can not let animal gender null!"));
        else if (!animal.getGender().equalsIgnoreCase("male") && !animal.getGender().equalsIgnoreCase("female"))
            exceptions.add(new IllegalStateException("There are only 2 types of gender: male / female"));

        if (!exceptions.isEmpty()) throw new MultipleExceptions(exceptions);
    }
    public void updateAnimalInformation(int id, AnimalUpdatingDTO request) {
        validateAnimalUpdate(request);
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalStateException("Animal with " + id + " is not found"));
        Cage cage = cageRepository.findCageById(animal.getCageId());
        if (cage != null) {
            if (request.getName() != null) animal.setName(request.getName());
            if (request.getDob() != null) animal.setDob(request.getDob());
            if (request.getDez() != null) animal.setDez(request.getDez());
            if (request.getGender() != null) animal.setGender(request.getGender());
            animal.setCageId(cage.getId());
        }
        animalRepository.save(animal);
    }

    public void deleteAnimal(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalStateException("Animal with " + id + " is not found"));
        animal.setStatus("Dead");
        animalRepository.save(animal);
        Cage cage = cageRepository.findCageById(animal.getCageId());
        int aliveAnimalInCage = animalRepository.findAllAliveInCage(animal.getCageId()).size();
        if (cage != null) {
            cage.setQuantity(aliveAnimalInCage);
            cageRepository.save(cage);
        }
    }

    public List<Animal> getSickAnimal(String email) {
        String type = "Health";
        List<Animal> animals = new ArrayList<>();
        List<Integer> animalId = new ArrayList<>();

        List<Cage> cages = cageRepository.findCagesByExpertEmail(email);
        for (Cage cage : cages
        ) {
            animalId.addAll(animalRepository.findIdByCageId(cage.getId()));
        }
        for (Integer id : animalId
        ) {
            if (!logRepository.findByAnimalIdAndTypeContaining(id, type).isEmpty()) {
                animals.add(animalRepository.findById(id).orElseThrow());
            }
        }
        return animals;
    }

    public List<Animal> searchAnimalByCageID(int cageID) {
        List<Animal> animalList = animalRepository.findBycageId(cageID);
        if (animalList.isEmpty()) throw new IllegalStateException("Search result returns null values !");
        return animalList;
    }

    public List<Animal> searchAnimalByCageName(String cageName) {
        List<Animal> animalList = animalRepository.findByCageName(cageName);
        if (animalList.isEmpty()) throw new IllegalStateException("Search result returns null values !");
        return animalList;
    }

    public List<Animal> searchAnimalByStaffEmail(String staffEmail) {
        List<Animal> animalList = animalRepository.findByStaffEmail(staffEmail);
        if (animalList.isEmpty()) throw new IllegalStateException("There are no animals under this staff control!");
        return animalList;
    }

    public void updateCageQuantity(int cageID) {
        List<Animal> animalList = animalRepository.findAnimalByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        cage.setQuantity(animalList.size());
        cageRepository.save(cage);
    }

    public void createAnimalMoveCageLog(Animal animal, Cage cageMoveTo, Cage animalCage) {
        logRepository.save(new AnimalLog(0, "Move cage", LocalDateTime.now(zone),
                "Move animal '" + animal.getName() + "' from cage '" +
                        animalCage.getName() + "' to cage '" + cageMoveTo.getName() + "'", animal.getId()));
    }

    public void moveCageAnimal(int animalID, AnimalMovingCageDTO request) {
        Animal animal = animalRepository.findByid(animalID); //Animal need to move
        Cage animalCage = cageRepository.findCageById(animal.getCageId()); //Current cage of animal
        Cage cageMoveTo = cageRepository.findCageById(request.getCageID()); //Cage need to move animal to

        Animal animalExistedInCageMoveTo = animalRepository.findFirstAnimalByCageId(cageMoveTo.getId()); //animal in cage need to move to
        if (animal.getStatus().equalsIgnoreCase("Dead")) throw new IllegalStateException("Can not move Dead animal");
        if (cageMoveTo.getCageType().equalsIgnoreCase("Close")) {
            animal.setCageId(cageMoveTo.getId());
            if (cageMoveTo.getQuantity() == 0) {
                cageMoveTo.setCageStatus("Owned");
                cageMoveTo.setName(request.getCageName());
            } else if (!animalExistedInCageMoveTo.getSpecie().equalsIgnoreCase(animal.getSpecie())) {
                throw new IllegalStateException("Can not move this animal because of difference in specie!");
            }
            animalRepository.save(animal);
            updateCageQuantity(cageMoveTo.getId());
            updateCageQuantity(animalCage.getId());
            if (!animalCage.equals(cageMoveTo)) {
                createAnimalMoveCageLog(animal, cageMoveTo, animalCage);
            }
        } else throw new IllegalStateException("Can not move this animal to 'Open' cage!");
    }
}


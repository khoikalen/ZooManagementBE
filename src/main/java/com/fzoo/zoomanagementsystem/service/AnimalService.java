package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.AnimalUpdatingDTO;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final CageRepository cageRepository;
    private final LogRepository logRepository;

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

    public void createNewAnimal(Animal animal, int cageID) {
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


    public void updateAnimalInformation(int id, AnimalUpdatingDTO request) {
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

    
}


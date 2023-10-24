package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.AnimalUpdatingDTO;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Log;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.LogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        return animalRepository.findAll(Sort.by(Sort.Direction.ASC, "cageId"));
    }

    public void createNewAnimal(Animal animal) {
        List<Animal> cageId = animalRepository.findBycageId(animal.getCageId());
        animal.setDez(LocalDate.now());
        if (!cageId.isEmpty())
            animalRepository.save(animal);
        else {
            throw new IllegalStateException("Cage not found");
        }
    }

    public List<Animal> searchAnimal(String animalName) {
        List<Animal> animals = animalRepository.findByname(animalName);
        return animals;
    }

    public void updateAnimalInformation(int id, AnimalUpdatingDTO request) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalStateException("Animal with " + id + " is not found"));
        Cage cage = cageRepository.findCageByName(request.getCageName());
        if (cage != null) {
            animal.setCage(cage);
            if (request.getName() != null) animal.setName(request.getName());
            if (request.getDob() != null) animal.setDob(request.getDob());
            if (request.getSex() != null) animal.setSex(request.getSex());
            if (request.getSpecie() != null) animal.setSpecie(request.getSpecie());
            if (request.getStatus() != null) animal.setStatus(request.getStatus());
        }
        animalRepository.save(animal);
    }

    public List<Animal> getSickAnimal(String email) {
        String type = "Health";
        List<Animal>animals = new ArrayList<>();
        List<Integer>animalId = new ArrayList<>();

        List<Cage> cages = cageRepository.findCagesByExpertEmail(email);
        List<Log> logs = new ArrayList<>();

        for (Cage cage : cages
        ) {
            animalId.addAll(animalRepository.findIdByCageId(cage.getId()));
        }

        for (Integer id : animalId
        ) {
            if(!logRepository.findByAnimalIdAndTypeContaining(id, type).isEmpty()){
                animals.add(animalRepository.findById(id).orElseThrow());
            }
        }

        return animals;

    }
}


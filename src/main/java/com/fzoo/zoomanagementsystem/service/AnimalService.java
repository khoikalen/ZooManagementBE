package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll(Sort.by(Sort.Direction.ASC, "cageId"));
    }

    public void createNewAnimal(Animal animal) {
        List<Animal> cageId = animalRepository.findBycageId(animal.getCageId());
        animal.setDez(LocalDate.now());
        if(!cageId.isEmpty())
        animalRepository.save(animal);
    }

    public List<Animal> searchAnimal(String animalName) {
        List<Animal> animals = animalRepository.findByname(animalName);
        return animals;
    }

}

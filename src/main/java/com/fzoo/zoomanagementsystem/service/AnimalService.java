package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.AnimalDTO;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import jakarta.persistence.ForeignKey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.module.ResolutionException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    public List<Animal> listOfAnimals(){
        return animalRepository.findAll();
    }

    public void createAnimal(Animal animal){
        long milis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(milis);
        animal.setDez(String.valueOf(date));
        List<Animal> cageId = animalRepository.findBycageId(animal.getCageId());
        if(!cageId.isEmpty()){
            animalRepository.save(animal);
        }
    }
}

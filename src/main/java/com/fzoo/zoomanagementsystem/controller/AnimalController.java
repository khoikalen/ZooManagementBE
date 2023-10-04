package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.AnimalDTO;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AnimalController {
    private final AnimalService animalService;
    @GetMapping("/v1/animal")
    public List<Animal> getListAnimals(){
        return animalService.listOfAnimals();
    }

    @PostMapping("/v1/animal")
    public void createNewAnimal(@RequestBody Animal animal){
        animalService.createAnimal(animal);
    }
}

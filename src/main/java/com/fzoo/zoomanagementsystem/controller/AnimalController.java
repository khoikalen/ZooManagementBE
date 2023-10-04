package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("v1/animal")
    public List<Animal> getListAnimal(){
        return animalService.getAllAnimals();
    }
    @PostMapping("v1/animal")
    public void createNewAnimal(@RequestBody Animal animal){
        animalService.createNewAnimal(animal);
    }

}

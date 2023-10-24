package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.AnimalUpdatingDTO;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("v1/animal")
    public List<Animal> getListAnimal() {
        return animalService.getAllAnimals();
    }

    @PostMapping("v1/animal")
    public void createNewAnimal(@RequestBody Animal animal) {
        animalService.createNewAnimal(animal);
    }

    @GetMapping("v1/animal/{animalName}")
    public List<Animal> searchAnimal(@PathVariable("animalName") String animalName) {
        return animalService.searchAnimal(animalName);
    }

    @PutMapping("v1/animal/{animalID}")
    public void updateAnimalInfomation(@PathVariable("animalID") int id, @RequestBody AnimalUpdatingDTO request) {
        animalService.updateAnimalInformation(id, request);
    }

    @Operation(
            summary = "List sick animal",
            description = "List sick animal that expert manage "
    )
    @GetMapping("v2/animal/{expertEmail}")
    public List<Animal> getSickAnimal(@PathVariable("expertEmail") String email) {
        return animalService.getSickAnimal(email);
    }

}

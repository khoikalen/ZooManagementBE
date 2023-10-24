package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.AnimalUpdatingDTO;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @Operation(
            summary = "List all Animals",
            description = "List all Animals exists in database"
    )
    @GetMapping("v1/animal")
    public List<Animal> getListAnimal() {
        return animalService.getAllAnimals();
    }

    @Operation(
            summary = "List all dead Animals",
            description = "List all dead Animals exists in database"
    )
    @GetMapping("v1/dead-animal")
    public List<Animal> getListDeadAnimal(){
        return animalService.getAllDeadAnimal();
    }

    @Operation(
            summary = "Search Animals using Animal Name",
            description = "Input Animal Name using String to find List of Animals"
    )
    @GetMapping("v2/animal/{animalName}")
    public List<Animal> searchAnimalByName(@PathVariable("animalName") String animalName) {
        return animalService.searchAnimalByName(animalName);
    }

    @Operation(
            summary = "Search an Animal using Animal ID",
            description = "Input Animal ID using int to find an Animal"
    )
    @GetMapping("v1/animal/{animalID}")
    public Animal searchAnimalByID(@PathVariable("animalID") int animalID){
        return animalService.searchAnimalByID(animalID);
    }

    @Operation(
            summary = "Search Animal in Cage",
            description = "Return a list of animals using CageID"
    )
    @GetMapping("v3/animal/{cageID}")
    public List<Animal> searchAnimalByCageID(@PathVariable("cageID") int cageID){
        return animalService.searchAnimalByCageId(cageID);
    }

    @GetMapping("v4/animal/{cageName}")
    public List<Animal> searchAnimalByCageName(@PathVariable("cageName") String cageName){
        return animalService.searchAnimalByCageName(cageName);
    }
    @Operation(
            summary = "Create an Animal",
            description = "Create an new Animal with following Input values"
    )
    @PostMapping("v1/animal")
    public void createNewAnimal(@RequestBody Animal animal) {
        animalService.createNewAnimal(animal);
    }


    @Operation(
            summary = "Update Animal information",
            description = "Input AnimalID and values to update an existed Animal"
    )
    @PutMapping("v1/animal/{animalID}")
    public void updateAnimalInformation(@PathVariable("animalID") int id, @RequestBody AnimalUpdatingDTO request) {
        animalService.updateAnimalInformation(id, request);
    }


    @Operation(
            summary = "Delete Animal information",
            description = "Using when an Animal status is 'Dead', input AnimalID and then the system will update Animal status to 'Dead'"
    )
    @DeleteMapping("v1/animal/{animalID}")
    public void deleteAnimal(@PathVariable("animalID") int id){
        animalService.deleteAnimal(id);
    }

}

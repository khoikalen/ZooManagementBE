package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.AnimalMovingCageDTO;
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
            summary = "Search Animals by staff email",
            description = "Input staff email from login sesion and list all animals under their control"
    )
    @GetMapping("v6/animal/{staffEmail}")
    public List<Animal> searchAnimalsByStaffEmail(@PathVariable("staffEmail") String staffEmail){
        return animalService.searchAnimalByStaffEmail(staffEmail);
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
            summary = "Search Animals by CageID",
            description = "Show list of Animals using CageID"
    )
    @GetMapping("v4/animal/{cageID}")
    public List<Animal> searchAnimalByCageID(@PathVariable("cageID") int cageID){
        return animalService.searchAnimalByCageID(cageID);
    }

    @Operation(
            summary = "Search Animals by CageName",
            description = "Show list of Animals using CageName"
    )
    @GetMapping("v5/animal/{cageName}")
    public List<Animal> searchAnimalByCageName(@PathVariable("cageName") String cageName){
        return animalService.searchAnimalByCageName(cageName);
    }

    @Operation(
            summary = "Create an Animal",
            description = "Create an new Animal with following Input values"
    )
    @PostMapping("v1/animal/{CageID}")
    public void createNewAnimal(@RequestBody Animal animal, @PathVariable("CageID") int cageID) {
        animalService.createNewAnimal(animal, cageID);
    }


    @Operation(
            summary = "Update Animal information",
            description = "Input AnimalID and values to update an existed Animal"
    )
    @PutMapping("v1/animal/{animalID}")
    public void updateAnimalInfomation(@PathVariable("animalID") int id, @RequestBody AnimalUpdatingDTO request) {
        animalService.updateAnimalInformation(id, request);
    }

//    @Operation(
//            summary = "List sick animal",
//            description = "List sick animal that expert manage "
//    )
//    @GetMapping("v3/animal/{expertEmail}")
//    public List<Animal> getSickAnimal(@PathVariable("expertEmail") String email) {
//        return animalService.getSickAnimal(email);
//    }

    @Operation(
            summary = "Delete Animal information",
            description = "Using when an Animal status is 'Dead', input AnimalID and then the system will update Animal status to 'Dead'"
    )
    @DeleteMapping("v1/animal/{animalID}")
    public void deleteAnimal(@PathVariable("animalID") int id){
        animalService.deleteAnimal(id);
    }


    @Operation(
            summary = "Move caage for animal",
            description = "Move cage for An animal base on animalID"
    )
    @PutMapping("v2/animal/{animalID}")
    public void moveCageAnimal(@PathVariable("animalID") int animalID, @RequestBody AnimalMovingCageDTO request){
        animalService.moveCageAnimal(animalID, request);
    }
}


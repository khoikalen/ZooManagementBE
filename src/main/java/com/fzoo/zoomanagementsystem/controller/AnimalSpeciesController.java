package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.UnidentifiedAnimal;
import com.fzoo.zoomanagementsystem.service.AnimalSpeciesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class AnimalSpeciesController {
    private final AnimalSpeciesService animalSpeciesService;
    @Operation(
            summary = "Get all Animal Species",
            description = "Get all Animal Species in Database"
    )
    @GetMapping("v1/species")
    public List<UnidentifiedAnimal> getAllAnimalSpecies(){
        return animalSpeciesService.getAllAnimalSpecies();
    }

    @Operation(
            summary = "Get Animal Species by ID",
            description = "Search Animal Species using ID"
    )
    @GetMapping("v1/species/{animalspecieID}")
        public UnidentifiedAnimal getAnimalSpeciesById(@PathVariable("animalspecieID") int animalSpecieID){
            return animalSpeciesService.getAnimalSpeciesByID(animalSpecieID);
        }

    @Operation(
            summary = "Get Animal Species by name",
            description = "Search Animal Species using Name"
    )
    @GetMapping("v2/species/{animalspecieName}")
    public List<UnidentifiedAnimal> getAnimalSpeicesByName(@PathVariable("animalspecieName") String animalSpecieName){
        return animalSpeciesService.getAnimalSpeciesByName(animalSpecieName);
    }

    @Operation(
            summary = "Get Animal Species by Cage ID",
            description = "Get list of Animal Species using Cage ID"
    )
    @GetMapping("v3/species/{cageID}")
    public List<UnidentifiedAnimal> getAnimalSpeciesByCageID(@PathVariable("cageID") int cageID){
        return animalSpeciesService.getAnimalSpeciesByCageID(cageID);
    }

    @Operation(
            summary = "Create Animal Species",
            description = "Create Animal Species base on inputted value"
    )
    @PostMapping("v1/species")
    public void createAnimalSpecies(@RequestBody UnidentifiedAnimal unidentifiedAnimal){
        animalSpeciesService.CreateAnimalSpecies(unidentifiedAnimal);
    }

    @Operation(
            summary = "Update Animal Species",
            description = "Input value to change Animal Species information"
    )
    @PutMapping("v1/species/{animalspecieID}")
    public void updateAnimalSpecies(@PathVariable("animalspecieID") int animalSpecieID, @RequestBody UnidentifiedAnimal request){
        animalSpeciesService.UpdateAnimalSpecies(animalSpecieID, request);
    }

    @Operation(
            summary = "Delete Animal Species",
            description = "Delete Animals Species using ID"
    )
    @DeleteMapping("/v1/species/{animalspecieID}")
    public void deleteAnimalSpecies(@PathVariable("animalspecieID") int animalSpecieID){
        animalSpeciesService.deleteAnimalSpecies(animalSpecieID);
    }
}

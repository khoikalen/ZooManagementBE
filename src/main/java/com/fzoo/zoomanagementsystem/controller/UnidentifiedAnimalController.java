package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.UnidentifiedAnimalMovingCageDTO;
import com.fzoo.zoomanagementsystem.model.UnidentifiedAnimal;
import com.fzoo.zoomanagementsystem.service.UnidentifiedAnimalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class UnidentifiedAnimalController {
    private final UnidentifiedAnimalService unidentifiedAnimalService;
    @Operation(
            summary = "Get all Unidentified Animals",
            description = "Get all Unidentified Animals in Database"
    )
    @GetMapping("v1/unidentified-animal")
    public List<UnidentifiedAnimal> getAllAnimalSpecies(){
        return unidentifiedAnimalService.getAllAnimalSpecies();
    }

    @Operation(
            summary = "Get Unidentified Animals by ID",
            description = "Search Unidentified Animal using ID"
    )
    @GetMapping("v1/unidentified-animal/{unidentified-animalID}")
        public UnidentifiedAnimal getAnimalSpeciesById(@PathVariable("unidentified-animalID") int animalSpecieID){
            return unidentifiedAnimalService.getAnimalSpeciesByID(animalSpecieID);
        }

    @Operation(
            summary = "Get Unidentified Animal by name",
            description = "Search Unidentified Animal using Name"
    )
    @GetMapping("v2/unidentified-animal/{unidentified-animalName}")
    public List<UnidentifiedAnimal> getAnimalSpeicesByName(@PathVariable("unidentified-animalName") String animalSpecieName){
        return unidentifiedAnimalService.getAnimalSpeciesByName(animalSpecieName);
    }

    @Operation(
            summary = "Get Unidentified Animal by Cage ID",
            description = "Get list of Unidentified Animals using Cage ID"
    )
    @GetMapping("v3/unidentified-animal/{cageID}")
    public List<UnidentifiedAnimal> getAnimalSpeciesByCageID(@PathVariable("cageID") int cageID){
        return unidentifiedAnimalService.getAnimalSpeciesByCageID(cageID);
    }

    @Operation(
            summary = "Get Unidentified Animal by Staff Email",
            description = "List all Unidentified Animal under Staff control"
    )
    @GetMapping("v4/unidentified-animal/{staffEmail}")
    public List<UnidentifiedAnimal> getUnidentifiedAnimalByStaffEmail(@PathVariable("staffEmail") String staffEmail){
        return unidentifiedAnimalService.searchUnidentifiedAnimalByStaffEmail(staffEmail);
    }

    @Operation(
            summary = "Create Unidentified Animals",
            description = "Create Unidentified Animals base on inputted value"
    )
    @PostMapping("v1/unidentified-animal/{cageID}")
    public void createAnimalSpecies(@RequestBody UnidentifiedAnimal unidentifiedAnimal, @PathVariable("cageID") int cageID){
        unidentifiedAnimalService.CreateAnimalSpecies(unidentifiedAnimal, cageID);
    }

    @Operation(
            summary = "Update Unidentified Animal",
            description = "Input value to change Unidentified Animal information"
    )
    @PutMapping("v1/unidentified-animal/{unidentified-animalID}")
    public void updateAnimalSpecies(@PathVariable("unidentified-animalID") int animalSpecieID, @RequestBody UnidentifiedAnimal request){
        unidentifiedAnimalService.UpdateAnimalSpecies(animalSpecieID, request);
    }

    @PutMapping("v2/unidentified-animal/{unidentified-animalID}")
    public void moveCageForUnidentifiedAnimal(@PathVariable("unidentified-animalID") int unidentifiedAnimalID, @RequestBody UnidentifiedAnimalMovingCageDTO request){
        unidentifiedAnimalService.moveCageUnidentifiedAnimal(unidentifiedAnimalID, request);
    }
    @Operation(
            summary = "Delete Unidentified Animal",
            description = "Delete Unidentified Animal using ID"
    )
    @DeleteMapping("/v1/unidentified-animal/{unidentified-animalID}")
    public void deleteAnimalSpecies(@PathVariable("unidentified-animalID") int animalSpecieID){
        unidentifiedAnimalService.deleteAnimalSpecies(animalSpecieID);
    }
}

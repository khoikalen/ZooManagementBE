package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.AnimalSpecies;
import com.fzoo.zoomanagementsystem.service.AnimalSpeciesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class AnimalSpeciesController {
    private final AnimalSpeciesService animalSpeciesService;
    @GetMapping("v1/species")
    public List<AnimalSpecies> getAllAnimalSpecie(){
        return animalSpeciesService.getAllAnimalSpecies();
    }

    @GetMapping("v1/species/{animalspecieID}")
        public Optional<AnimalSpecies> getAnimalById(@PathVariable("animalspecieID") int animalSpecieID){
            return animalSpeciesService.getAnimalSpeciesByID(animalSpecieID);
        }
    @GetMapping("v2/species/{animalspecieName}")
    public List<AnimalSpecies> getAnimalByName(@PathVariable("animalspecieName") String animalSpecieName){
        return animalSpeciesService.getAnimalSpeciesByName(animalSpecieName);
    }

}

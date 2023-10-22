package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.AnimalSpecies;
import com.fzoo.zoomanagementsystem.repository.AnimalSpeciesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalSpeciesService {
    private final AnimalSpeciesRepository animalSpeciesRepository;
    public List<AnimalSpecies> getAllAnimalSpecies(){

        return animalSpeciesRepository.findAll(Sort.by(Sort.Direction.ASC, "cageId"));
    }
    public Optional<AnimalSpecies> getAnimalSpeciesByID(int id){
        return animalSpeciesRepository.findById(id);
    }
    public List<AnimalSpecies> getAnimalSpeciesByName(String animalSpecieName){
        return animalSpeciesRepository.findByName(animalSpecieName);
    }
    public void UpdateAnimalSpecies(int animalSpecieID, AnimalSpecies request){
        AnimalSpecies animalSpecies = animalSpeciesRepository.findById(animalSpecieID).orElseThrow(() -> new IllegalStateException("Animal specie with " + animalSpecieID + " is not found"));
        if(request.getName() != null) animalSpecies.setName(request.getName());
        animalSpecies.setQuantity(request.getQuantity());
        animalSpecies.setCageId(request.getCageId());
        animalSpeciesRepository.save(animalSpecies);
    }
}

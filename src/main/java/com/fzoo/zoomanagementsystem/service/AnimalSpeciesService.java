package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.AnimalSpecies;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.repository.AnimalSpeciesRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnimalSpeciesService {
    private final AnimalSpeciesRepository animalSpeciesRepository;
    private final CageRepository cageRepository;

    public List<AnimalSpecies> getAllAnimalSpecies() {
        List<AnimalSpecies> animalSpeciesList = animalSpeciesRepository.findAll(Sort.by(Sort.Direction.ASC, "cageId"));
        if (animalSpeciesList.isEmpty()) throw new IllegalStateException("There are no Animal Species");
        return animalSpeciesList;
    }

    public AnimalSpecies getAnimalSpeciesByID(int id) {
        return animalSpeciesRepository.findById(id).orElseThrow(() -> new IllegalStateException("No result for Animal Species searching!"));
    }

    public List<AnimalSpecies> getAnimalSpeciesByName(String animalSpecieName) {
        List<AnimalSpecies> animalSpeciesList = animalSpeciesRepository.findByName(animalSpecieName);
        if (animalSpeciesList.isEmpty()) throw new IllegalStateException("No result for Animal Species searching");
        return animalSpeciesList;
    }

    public List<AnimalSpecies> getAnimalSpeciesByCageID(int cageID) {
        List<AnimalSpecies> animalSpeciesList = animalSpeciesRepository.findByCageId(cageID);
        if (animalSpeciesList.isEmpty()) throw new IllegalStateException("No result for Animal Species searching");
        return animalSpeciesList;
    }

    public void updateCageQuantity(int cageID) {
        List<AnimalSpecies> animalSpeciesList = animalSpeciesRepository.findByCageId(cageID);
        Cage cage = cageRepository.findCageById(cageID);
        int cageQuantity = 0;
        for (AnimalSpecies animalSpecies : animalSpeciesList) {
            cageQuantity += animalSpecies.getQuantity();
        }
        cage.setQuantity(cageQuantity);
        cageRepository.save(cage);
    }

    public void UpdateAnimalSpecies(int animalSpecieID, AnimalSpecies request) {
        AnimalSpecies animalSpecies = animalSpeciesRepository.findById(animalSpecieID).orElseThrow(() -> new IllegalStateException("Animal specie with " + animalSpecieID + " is not found"));
        if (request.getName() != null) animalSpecies.setName(request.getName());
        animalSpecies.setQuantity(request.getQuantity());
        animalSpecies.setCageId(request.getCageId());
        animalSpeciesRepository.save(animalSpecies);
        updateCageQuantity(animalSpecies.getCageId());
    }

    public void CreateAnimalSpecies(AnimalSpecies animalSpecies) {
        List<AnimalSpecies> animalSpeciesList = animalSpeciesRepository.findByCageId(animalSpecies.getCageId());
        Cage cage = cageRepository.findCageById(animalSpecies.getCageId());
        boolean isDuplicated = false;
        for (AnimalSpecies species : animalSpeciesList) {
            if (species.getName().equalsIgnoreCase(animalSpecies.getName())) isDuplicated = true;
        }
        if (cage != null && !isDuplicated) {
            animalSpeciesRepository.save(animalSpecies);
        } else if (cage != null && isDuplicated)
            throw new IllegalStateException("This Animal Species is already exists in the cage");
        else throw new IllegalStateException("Cage not found");
        updateCageQuantity(animalSpecies.getCageId());
    }

    public void deleteAnimalSpecies(int animalSpeicesID) {
        AnimalSpecies animalSpecies = animalSpeciesRepository.findById(animalSpeicesID).orElseThrow(() -> new IllegalStateException("Can not find Animal Species to delete"));
        animalSpeciesRepository.deleteById(animalSpeicesID);
        updateCageQuantity(animalSpecies.getCageId());
    }
}

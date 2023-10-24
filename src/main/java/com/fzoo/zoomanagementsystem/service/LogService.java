package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.LogHealthResponse;
import com.fzoo.zoomanagementsystem.dto.LogRequest;
import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Log;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final CageRepository cageRepository;
    private final AnimalRepository animalRepository;

    public void add(int id, LogRequest request) {
        Log log = Log.builder()
                .type(request.getType())
                .shortDescription(request.getShortDescription())
                .dateTime(LocalDateTime.now())
                .animalId(id)
                .build();
        logRepository.save(log);

    }

    public List<Log> showLogByAnimal(int id) {
        return logRepository.findLogByAnimalIdOrderByDateTimeDesc(id);
    }

    public List<LogHealthResponse> getLogByHealth(String email) {
        String type = "Health";
        List<Animal> animals = new ArrayList<>();
        List<LogHealthResponse> responseList = new ArrayList<>();
        List<Log> logs = new ArrayList<>();
        List<Cage> cages = cageRepository.findCagesByExpertEmail(email);
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        for (Cage cage : cages
        ) {
            animals.addAll(animalRepository.findBycageId(cage.getId()));
        }

        for (Animal animal : animals
        ) {
            logs.addAll(logRepository.findByAnimalIdAndTypeContaining(animal.getId(), type));
        }

        for (Log log : logs
        ) {

            if (log.getDateTime().isAfter(oneDayAgo)) {
                Animal animal = animalRepository.findById(log.getAnimalId()).orElseThrow(() -> new IllegalStateException("does not have animal"));
                LogHealthResponse logHealthResponse = LogHealthResponse.builder()
                        .name(animal.getName())
                        .species(animal.getName())
                        .shortDescription(log.getShortDescription())
                        .LocalDateTime(log.getDateTime())
                        .build();
                responseList.add(logHealthResponse);
            }
        }

        return responseList;
    }
}

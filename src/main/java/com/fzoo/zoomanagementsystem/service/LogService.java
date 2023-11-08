package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.LogHealthResponse;
import com.fzoo.zoomanagementsystem.dto.LogRequest;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final CageRepository cageRepository;
    private final AnimalRepository animalRepository;
    private final UnidentifiedAnimalLogRepository unidentifiedAnimalLogRepository;
    private final UnidentifiedAnimalRepository unidentifiedAnimalRepository;
    private final CageService cageService;

    ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
    public void add(int id, LogRequest request) {
        AnimalLog animalLog = AnimalLog.builder()
                .type(request.getType())
                .shortDescription(request.getShortDescription())
                .dateTime(LocalDateTime.now(zone))
                .animalId(id)
                .build();
        logRepository.save(animalLog);

    }

    public void addUnidentifiedAnimal(int id, LogRequest request) {
        UnidentifiedAnimalLog unidentifiedAnimalLog = UnidentifiedAnimalLog.builder()
                .type(request.getType())
                .shortDescription(request.getShortDescription())
                .dateTime(LocalDateTime.now(zone))
                .unidentifiedAnimalId(id)
                .build();
        unidentifiedAnimalLogRepository.save(unidentifiedAnimalLog);

    }

    public List<AnimalLog> showLogByAnimal(int id) {
        return logRepository.findLogByAnimalIdOrderByDateTimeDesc(id);
    }


    public List<UnidentifiedAnimalLog> showLogByUnidentifiedAnimal(int id) {
        return unidentifiedAnimalLogRepository.findLogByUnidentifiedAnimalIdOrderByDateTimeDesc(id);
    }

    public List<LogHealthResponse> getLogByHealth(String email) {
        String type = "Health";
        List<Animal> animals = new ArrayList<>();
        List<UnidentifiedAnimal> unidentifiedAnimals = new ArrayList<>();
        List<LogHealthResponse> responseList = new ArrayList<>();
        List<AnimalLog> animalLogs = new ArrayList<>();
        List<UnidentifiedAnimalLog>unidentifiedAnimalLogs=new ArrayList<>();
        List<Cage> cages = cageService.getCagesByExpertEmail(email).getContent();
        LocalDateTime oneDayAgo = LocalDateTime.now(zone).minusDays(1);
        for (Cage cage : cages
        ) {
            if(animalRepository.findBycageId(cage.getId()).isEmpty()){
                unidentifiedAnimals.addAll(unidentifiedAnimalRepository.findByCageId(cage.getId()));
            }else{
                 animals.addAll(animalRepository.findBycageId(cage.getId()));
            }
        }
        for (UnidentifiedAnimal uAnimals: unidentifiedAnimals
             ) {
            unidentifiedAnimalLogs.addAll(unidentifiedAnimalLogRepository.findByUnidentifiedAnimalIdAndTypeContaining(uAnimals.getId(),type));
        }

        for (Animal animal : animals
        ) {
            animalLogs.addAll(logRepository.findByAnimalIdAndTypeContaining(animal.getId(), type));
        }

        for (UnidentifiedAnimalLog uAnimalLog: unidentifiedAnimalLogs
             ) {
            if (uAnimalLog.getDateTime().isAfter(oneDayAgo)) {
                UnidentifiedAnimal animal = unidentifiedAnimalRepository.findById(uAnimalLog.getUnidentifiedAnimalId()).orElseThrow();
                LogHealthResponse logHealthResponse = LogHealthResponse.builder()
                        .name(animal.getName())
                        .shortDescription(uAnimalLog.getShortDescription())
                        .LocalDateTime(uAnimalLog.getDateTime())
                        .build();
                responseList.add(logHealthResponse);
            }

        }
        for (AnimalLog animalLog : animalLogs
        ) {

            if (animalLog.getDateTime().isAfter(oneDayAgo)) {
                Animal animal = animalRepository.findById(animalLog.getAnimalId()).orElseThrow(() -> new IllegalStateException("does not have animal"));
                LogHealthResponse logHealthResponse = LogHealthResponse.builder()
                        .name(animal.getName())
                        .species(animal.getSpecie())
                        .shortDescription(animalLog.getShortDescription())
                        .LocalDateTime(animalLog.getDateTime())
                        .build();
                responseList.add(logHealthResponse);
            }
        }
        return responseList.stream().sorted(Comparator
                        .comparing(LogHealthResponse::getLocalDateTime).reversed())
                        .collect(Collectors.toList());
    }


}

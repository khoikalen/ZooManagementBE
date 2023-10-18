package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.LogRequest;
import com.fzoo.zoomanagementsystem.model.Log;
import com.fzoo.zoomanagementsystem.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
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
}

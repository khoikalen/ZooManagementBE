package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CageService {

    private final CageRepository cageRepository;

    public List<Cage> getAllCages() {
        return cageRepository.findAll();
    }
}

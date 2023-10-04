package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.service.CageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cage")
@RequiredArgsConstructor
public class CageController {

    private final CageService cageService;

    @GetMapping
    public List<Cage> getAllCages() {
        return cageService.getAllCages();
    }
}

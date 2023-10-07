package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.CageRequest;
import com.fzoo.zoomanagementsystem.dto.CageViewDTO;
import com.fzoo.zoomanagementsystem.exception.UserNotFoundException;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.service.CageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cage")
@RequiredArgsConstructor
public class CageController {

    private final CageService cageService;

    @Operation(
            summary = "List all the cages",
            description = "List all the cages from the database"
    )
    @GetMapping
    public List<CageViewDTO> getAllCages() {
        return cageService.getAllCages();
    }

    @Operation(
            summary = "Find a cage by ID",
            description = "Find and response specific cage by the ID"
    )
    @GetMapping("{cageId}")
    public Cage getCage(@PathVariable("cageId") int cageId) {
        return cageService.getCageById(cageId);
    }


    @Operation(
            summary ="Create a new cage",
            description = "Create an empty cage and an owned cage"
    )
    @PostMapping
    public void createNewCage(@RequestBody @Valid CageRequest cage) throws UserNotFoundException {
        cageService.addNewCage(cage);
    }
}

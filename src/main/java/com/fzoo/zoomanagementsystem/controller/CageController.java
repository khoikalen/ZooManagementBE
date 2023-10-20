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
@RequestMapping("api")
@RequiredArgsConstructor
public class CageController {

    private final CageService cageService;

    @Operation(
            summary = "List all the cages",
            description = "List all the cages from the database"
    )
    @GetMapping("/v1/cage")
    public List<CageViewDTO> getAllCages() {
        return cageService.getAllCages();
    }

    @Operation(
            summary = "Find a cage by ID",
            description = "Find and response specific cage by the ID"
    )
    @GetMapping("/v1/cage/{cageId}")
    public Cage getCage(@PathVariable("cageId") int cageId) throws UserNotFoundException {
        return cageService.getCageById(cageId);
    }

    @Operation(
            summary = "Get all cages by expert email",
            description = "Get all cages that the specific expert manage"
    )
    @GetMapping("/v2/cage/{expertEmail}")
    public List<CageViewDTO> getCagesOfExpert(@PathVariable("expertEmail") String expertEmail) {
        return cageService.getCagesByExpertEmail(expertEmail);
    }

    @Operation(
            summary = "Get all cages by staff email",
            description = "Get all cages that the specific staff manage"
    )
    @GetMapping("/v3/cage/{staffEmail}")
    public List<CageViewDTO> GetCagesOfStaff(@PathVariable("staffEmail") String staffEmail) {
        return cageService.getCagesByStaffEmail(staffEmail);
    }

    @Operation(
            summary ="Create a new cage",
            description = "Create an empty cage and an owned cage"
    )
    @PostMapping("/v1/cage")
    public void createNewCage(@RequestBody @Valid CageRequest cage) throws UserNotFoundException {
        cageService.addNewCage(cage);
    }

    @Operation(
            summary ="Update a cage by ID",
            description = "Update the cage with validation"
    )
    @PutMapping("/v1/cage/{cageId}")
    public void updateCage(@PathVariable("cageId") int cageId ,@RequestBody @Valid CageRequest request) throws UserNotFoundException {
        cageService.updateCage(cageId, request);
    }

    @Operation(
            summary = "Delete a cage by ID",
            description = "Delete a cage by ID if that cage is empty"
    )
    @DeleteMapping("/v1/cage/{cageId}")
    public void deleteCage(@PathVariable("cageId") int cageId) {
        cageService.deleteCage(cageId);
    }
}

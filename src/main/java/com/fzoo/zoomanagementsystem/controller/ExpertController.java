package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.auth.AuthenticationResponse;
import com.fzoo.zoomanagementsystem.auth.AuthenticationService;
import com.fzoo.zoomanagementsystem.dto.ExpertAccountRequest;
import com.fzoo.zoomanagementsystem.dto.ExpertRequest;
import com.fzoo.zoomanagementsystem.model.Expert;
import com.fzoo.zoomanagementsystem.service.ExpertService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final AuthenticationService authService;

    @Operation(
            summary = "List all the experts",
            description = "List all the experts from the database"
    )
    @GetMapping("/v1/expert")
    public List<ExpertRequest> getAllExperts() {
        return expertService.getAllExperts();
    }

    @Operation(
            summary = "Get a expert by ID",
            description = "Find and response a specific expert base on ID"
    )
    @GetMapping("/v1/expert/{expertId}")
    public Expert getExpertById(@PathVariable("expertId") int expertId) {
        return expertService.getExpertById(expertId);
    }

    @Operation(
            summary = "Create a new expert",
            description = "Create a expert and also a expert account"
    )
    @PostMapping("/v1/expert")
    public ResponseEntity<AuthenticationResponse> registerNewExpert(@RequestBody @Valid ExpertAccountRequest request) {
        return ResponseEntity.ok(authService.registerNewExpert(request));
    }

    @Operation(
            summary = "Delete an expert by ID",
            description = "Delete an expert and an expert account"
    )
    @DeleteMapping("/v1/expert/{expertId}")
    public void deleteExpert(@PathVariable("expertId") int expertId) {
        expertService.deleteExpertById(expertId);
    }

    @Operation(
            summary = "Update an expert by ID",
            description = "Update an specific expert with validation"
    )
    @PutMapping("/v1/expert/{expertId}")
    public void updateExpert(@PathVariable("expertId") int expertId, @RequestBody @Valid ExpertRequest request) {
        expertService.updateExpert(expertId, request);
    }
}

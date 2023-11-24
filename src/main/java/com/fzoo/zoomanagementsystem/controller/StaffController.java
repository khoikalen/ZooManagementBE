package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.AuthenticationResponse;
import com.fzoo.zoomanagementsystem.service.AuthenticationService;
import com.fzoo.zoomanagementsystem.dto.StaffAccountRequest;
import com.fzoo.zoomanagementsystem.dto.UpdatedStaff;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final AuthenticationService authService;

    @Operation(
            summary = "List all the staffs",
            description = "List all the staffs that exist in the database"
    )
    @GetMapping("/v1/staff")
    public List<Staff> getAllStaffs() {
        return staffService.getAllStaffs();
    }

    @Operation(
            summary = "Find a staff by ID",
            description = "Find and response specific staff by the ID"
    )
    @GetMapping("/v1/staff/{staffId}")
    public Staff getStaffById(@PathVariable("staffId") int staffId) {
        return staffService.getStaffById(staffId);
    }

    @Operation(
            summary = "Search staffs by name",
            description = "Search staffs by finding the similar first name or last name"
    )
    @GetMapping("/v2/staff/{staffName}")
    public List<Staff> searchStaffs(@PathVariable("staffName") String name) {
        return staffService.searchStaffbyName(name);
    }

    @Operation(
            summary = "Create a new staff",
            description = "Create a new staff, besides, create an new account for that staff"
    )
    @PostMapping("/v1/staff")
    public ResponseEntity<AuthenticationResponse> registerNewStaff(@RequestBody @Valid StaffAccountRequest request) {
        return ResponseEntity.ok(authService.registerNewStaff(request));
    }

    @Operation(
            summary = "Delete a staff by ID",
            description = "Delete a staff, besides, delete his/her account and delete the constraint between him/her and the cage which they used to manage"
    )
    @DeleteMapping(path = "/v1/staff/{staffId}")
    public void deleteStaff(@PathVariable("staffId") int staffId) {
        staffService.deleteStaff(staffId);
    }

    @Operation(
            summary = "Update a staff by ID",
            description = "Find a staff by ID then update their information"
    )
    @PutMapping(path="/v1/staff/{staffId}")
    public void updateStaff(@PathVariable("staffId") int staffId,
                            @RequestBody @Valid UpdatedStaff request) {
        staffService.updateStaff(staffId, request.getFirstName(), request.getLastName(), request.getGender(), request.getStartDay(), request.getPhoneNumber());
    }


}

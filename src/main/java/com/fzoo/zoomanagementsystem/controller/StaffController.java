package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.auth.AuthenticationResponse;
import com.fzoo.zoomanagementsystem.auth.AuthenticationService;
import com.fzoo.zoomanagementsystem.dto.StaffAccount;
import com.fzoo.zoomanagementsystem.dto.UpdatedStaff;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final AuthenticationService authService;

    @GetMapping("/v1/staff")
    public List<Staff> getAllStaffs() {
        return staffService.getAllStaffs();
    }

    @GetMapping("/v1/staff/{staffId}")
    public Staff getStaffById(@PathVariable("staffId") int staffId) {
        return staffService.getStaffById(staffId);
    }

    @GetMapping("/v2/staff/{staffName}")
    public List<Staff> searchStaffs(@PathVariable("staffName") String name) {
        return staffService.searchStaffbyName(name);
    }

    @PostMapping("/v1/staff")
    public ResponseEntity<AuthenticationResponse> registerNewStaff(@RequestBody @Valid StaffAccount request) {
        return ResponseEntity.ok(authService.registerNewStaff(request));
    }

    @DeleteMapping(path = "/v1/staff/{staffId}")
    public void deleteStaff(@PathVariable("staffId") int staffId) {
        staffService.deleteStaff(staffId);
    }

    @PutMapping(path="/v1/staff/{staffId}")
    public void updateStaff(@PathVariable("staffId") int staffId,
                            @RequestBody @Valid UpdatedStaff request) {
        staffService.updateStaff(staffId, request.getFirstName(), request.getLastName(), request.getSex(), request.getStartDay(), request.getPhoneNumber());
    }


}

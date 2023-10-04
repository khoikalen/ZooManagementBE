package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.auth.AuthenticationResponse;
import com.fzoo.zoomanagementsystem.auth.AuthenticationService;
import com.fzoo.zoomanagementsystem.dto.StaffAccount;
import com.fzoo.zoomanagementsystem.dto.UpdatedStaff;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final AuthenticationService authService;

    @GetMapping
    public List<Staff> getAllStaffs() {
        return staffService.getAllStaffs();
    }

    @GetMapping("{staffName}")
    public List<Staff> searchStaffs(@PathVariable("staffName") String name) {
        return staffService.searchStaffbyName(name);
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> registerNewStaff(@RequestBody StaffAccount request) {
        return ResponseEntity.ok(authService.registerNewStaff(request));
    }

    @DeleteMapping(path = "{staffId}")
    public void deleteStaff(@PathVariable("staffId") int staffId) {
        staffService.deleteStaff(staffId);
    }

    @PutMapping(path="{staffId}")
    public void updateStaff(@PathVariable("staffId") int staffId,
                            @RequestBody UpdatedStaff request) {
        staffService.updateStaff(staffId, request.getFirstName(), request.getLastName(), request.getSex(), request.getStartDay(), request.getPhoneNumber());
    }


}

package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.AuthenticationResponse;
import com.fzoo.zoomanagementsystem.service.AuthenticationService;
import com.fzoo.zoomanagementsystem.dto.AuthenticateRequest;
import com.fzoo.zoomanagementsystem.dto.RefreshTokenRequest;
import com.fzoo.zoomanagementsystem.dto.RegisterRequest;
import com.fzoo.zoomanagementsystem.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody RefreshTokenRequest request
            ){
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @DeleteMapping(path = "/logout")
    public void revokeToken(@RequestBody RefreshTokenRequest request){
           refreshTokenService.logout(request);
    }
}

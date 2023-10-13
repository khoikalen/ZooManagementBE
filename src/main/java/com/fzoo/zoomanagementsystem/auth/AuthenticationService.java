package com.fzoo.zoomanagementsystem.auth;

import com.fzoo.zoomanagementsystem.dto.AuthenticateRequest;
import com.fzoo.zoomanagementsystem.dto.ExpertAccountRequest;
import com.fzoo.zoomanagementsystem.dto.RegisterRequest;
import com.fzoo.zoomanagementsystem.dto.StaffAccountRequest;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
import com.fzoo.zoomanagementsystem.repository.AreaRepository;
import com.fzoo.zoomanagementsystem.repository.ExpertRepository;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import com.fzoo.zoomanagementsystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StaffRepository staffRepository;
    private final AccountRepository accountRepository;
    private final ExpertRepository expertRepository;
    private final AreaRepository areaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse registerNewStaff(StaffAccountRequest request) {
            boolean checkEmailInAccount = accountRepository.existsByEmail(request.getEmail());
            boolean checkEmailInStaff = staffRepository.existsByEmail(request.getEmail());
            boolean checkPhoneNumberInStaff = staffRepository.existsByPhoneNumber(request.getPhoneNumber());
            boolean checkPhoneNumberInExpert = expertRepository.existsByPhoneNumber(request.getPhoneNumber());
            if (!checkEmailInStaff && !checkEmailInAccount) {
                if (!checkPhoneNumberInStaff  && !checkPhoneNumberInExpert) {
                    var staff = Staff.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .sex(request.getSex())
                            .startDay(request.getStartDay())
                            .email(request.getEmail())
                            .phoneNumber(request.getPhoneNumber())
                            .build();
                    staffRepository.save(staff);
                    var account = Account.builder()
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(request.getRole())
                            .build();
                    accountRepository.save(account);
                } else {
                    throw new IllegalStateException("Phone number " + request.getPhoneNumber() + " is already existed!");
                }
            } else {
                throw new IllegalStateException("Email "+ request.getEmail()+" is already existed!");
            }
        return AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse registerNewExpert(ExpertAccountRequest request) {
        boolean checkEmailInAccount = accountRepository.existsByEmail(request.getEmail());
        boolean checkEmailInExpert = expertRepository.existsByEmail(request.getEmail());
        boolean checkPhoneNumberInStaff = staffRepository.existsByPhoneNumber(request.getPhoneNumber());
        boolean checkPhoneNumberInExpert = expertRepository.existsByPhoneNumber(request.getPhoneNumber());
        if (!checkEmailInAccount && !checkEmailInExpert) {
            if (!checkPhoneNumberInStaff && !checkPhoneNumberInExpert) {
                Area area = areaRepository.findAreaByName(request.getAreaName());
                if (area.getExpert() == null) {
                    var expert = Expert.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .sex(request.getSex())
                            .startDay(request.getStartDay())
                            .email(request.getEmail())
                            .phoneNumber(request.getPhoneNumber())
                            .areaId(area.getId())
                            .area(area)
                            .build();
                    expertRepository.save(expert);
                    var account = Account.builder()
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(request.getRole())
                            .build();
                    accountRepository.save(account);
                } else {
                    throw new IllegalStateException("Area " + request.getAreaName() + " have already had expert to manage");
                }
            } else {
                throw new IllegalStateException("Phone number " + request.getPhoneNumber() + " is already existed!");
            }
        } else {
            throw new IllegalStateException("Email "+ request.getEmail()+" is already existed!");
        }
        return AuthenticationResponse.builder().build();
    }


    public AuthenticationResponse register(RegisterRequest request){
        Optional<Account> byEmail = accountRepository.findByEmail(request.getEmail());
        if(byEmail.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        var account = Account.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()) )
                .role(Role.valueOf(request.getRole()))
                .build();
        accountRepository.save(account);
        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticateRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(account);
        return  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

}

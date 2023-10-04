package com.fzoo.zoomanagementsystem.auth;

import com.fzoo.zoomanagementsystem.dto.StaffAccount;
import com.fzoo.zoomanagementsystem.model.Account;
import com.fzoo.zoomanagementsystem.model.Role;
import com.fzoo.zoomanagementsystem.model.Staff;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StaffRepository staffRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse registerNewStaff(StaffAccount request) {
        if (request.getRole().equals(Role.STAFF)) {
            boolean checkEmail = staffRepository.existsByEmail(request.getEmail());
            boolean checkPhoneNumber = staffRepository.existsByPhoneNumber(request.getPhoneNumber());
            if (!checkEmail) {
                if (!checkPhoneNumber) {
                    var staff = Staff.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .sex(request.getSex())
                            .startDay(request.getStartDay())
                            .email(request.getEmail())
                            .phoneNumber(request.getPhoneNumber())
                            .build();
                    staffRepository.save(staff);
                } else {
                    throw new IllegalStateException("Phone number " + request.getPhoneNumber() + " is already existed!");
                }
            } else {
                throw new IllegalStateException("email "+ request.getEmail()+" is already existed!");
            }
        }
        boolean existEmail = accountRepository.existsByEmail(request.getEmail());
        if (!existEmail) {
            var account = Account.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();
            accountRepository.save(account);
        } else {
            throw new IllegalStateException("email "+ request.getEmail()+" is already existed!");
        }
        return AuthenticationResponse.builder().build();
    }
}

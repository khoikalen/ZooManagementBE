package com.fzoo.zoomanagementsystem.auth;

import com.fzoo.zoomanagementsystem.dto.StaffAccount;
import com.fzoo.zoomanagementsystem.model.Account;
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
        boolean exist = staffRepository.existsByEmail(request.getEmail());
        if (!exist) {
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
            throw new IllegalStateException("email "+ request.getEmail()+" is already existed!");
        }

        return AuthenticationResponse.builder().build();
    }
}

package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.ExpertRequest;
import com.fzoo.zoomanagementsystem.model.Account;
import com.fzoo.zoomanagementsystem.model.Area;
import com.fzoo.zoomanagementsystem.model.Expert;
import com.fzoo.zoomanagementsystem.model.RefreshToken;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final AccountRepository accountRepository;
    private final StaffRepository staffRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    public List<Expert> getAllExperts() {
        return expertRepository.findAll().stream().filter(expert -> expert.getStatus() != 0).toList();
    }

    public Expert getExpertById(int expertId) {
        Expert expert = expertRepository.findExpertById(expertId);
        if (expert.getStatus() == 0) {
            throw new IllegalStateException("This expert was no longer existed");
        }
        return expert;
    }

    @Transactional
    public void deleteExpertById(int expertId) {
        boolean checkExist = expertRepository.existsById(expertId);
        if (checkExist) {
            deleteExpertAccount(expertId);
            Expert expertToDelete = expertRepository.findExpertById(expertId);
            expertToDelete.setStatus(0);
            expertRepository.save(expertToDelete);
        } else {
            throw new IllegalStateException("Expert with id " + expertId + " does not exist");
        }
    }


    public void deleteExpertAccount(int expertId) {
        Optional<Expert> expert = expertRepository.findById(expertId);
        String email;
        if (expert.isPresent()) {
            email = expert.get().getEmail();
        } else {
            throw new IllegalStateException("Expert with id " + expertId + " does not exist");
        }
        Account account = accountRepository.findAccountByEmail(email);
        RefreshToken refreshToken = refreshTokenRepository.findByAccountInfo_Id(account.getId());
        if (refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
        }
        accountRepository.deleteAccountByEmail(email);
    }

    public void updateExpert(int expertId, ExpertRequest request) {
        Expert expert = expertRepository.findExpertById(expertId);
        if (expert.getStatus() == 1) {
            boolean checkPhoneNumberInStaff = staffRepository.existsByPhoneNumber(request.getPhoneNumber());
            boolean checkPhoneNumberInExpert = expertRepository.existsByPhoneNumber(request.getPhoneNumber());
            if (expert != null) {
                if ((!checkPhoneNumberInExpert && !checkPhoneNumberInStaff) || expert.getPhoneNumber().equals(request.getPhoneNumber())) {
                    expert.setFirstName(request.getFirstName());
                    expert.setLastName(request.getLastName());
                    expert.setGender(request.getGender());
                    expert.setStartDay(request.getStartDay());
                    expert.setPhoneNumber(request.getPhoneNumber());
                    expertRepository.save(expert);
                } else {
                    throw new IllegalStateException("Phone number " + request.getPhoneNumber() + " is already existed!");
                }
            } else {
                throw new IllegalStateException("Expert with ID " + expertId + " does not exist!");
            }
        } else {
            throw new IllegalStateException("This expert was no longer existed");
        }
    }
}

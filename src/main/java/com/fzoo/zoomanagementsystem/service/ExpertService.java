package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.ExpertRequest;
import com.fzoo.zoomanagementsystem.model.Area;
import com.fzoo.zoomanagementsystem.model.Expert;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
import com.fzoo.zoomanagementsystem.repository.AreaRepository;
import com.fzoo.zoomanagementsystem.repository.ExpertRepository;
import com.fzoo.zoomanagementsystem.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final AccountRepository accountRepository;
    private final AreaRepository areaRepository;
    private final StaffRepository staffRepository;

    public List<ExpertRequest> getAllExperts() {
         List<Expert> expertList = expertRepository.findAll();
         List<ExpertRequest> expertView = new ArrayList<>();
        for (Expert expert: expertList) {
            expertView.add(new ExpertRequest(
                    expert.getId(),
                    expert.getFirstName(),
                    expert.getLastName(),
                    expert.getGender(),
                    expert.getStartDay(),
                    expert.getEmail(),
                    expert.getPhoneNumber()));
        }
        return expertView;
    }

    public Expert getExpertById(int expertId) {
        return expertRepository.findExpertById(expertId);
    }

    @Transactional
    public void deleteExpertById(int expertId) {
        boolean checkExist = expertRepository.existsById(expertId);
        if (checkExist) {
            setNoOne(expertId);
            deleteExpertAccount(expertId);
            expertRepository.deleteById(expertId);
        } else {
            throw new IllegalStateException("Expert with id " + expertId + " does not exist");
        }
    }

    public void setNoOne(int expertId) {
        Expert expert = expertRepository.findExpertById(expertId);
//        Area area = areaRepository.findAreaByExpert(expert);
    }

    public void deleteExpertAccount(int expertId) {
        Optional<Expert> expert = expertRepository.findById(expertId);
        String email;
        if (expert.isPresent()) {
            email = expert.get().getEmail();
        } else {
            throw new IllegalStateException("Expert with id " + expertId + " does not exist");
        }
        accountRepository.deleteAccountByEmail(email);
    }

    public void updateExpert(int expertId, ExpertRequest request) {
        Expert expert = expertRepository.findExpertById(expertId);

        boolean checkPhoneNumberInStaff = staffRepository.existsByPhoneNumber(request.getPhoneNumber());
        boolean checkPhoneNumberInExpert = expertRepository.existsByPhoneNumber(request.getPhoneNumber());

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

    }
}

package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Expert;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
import com.fzoo.zoomanagementsystem.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final AccountRepository accountRepository;

    public List<Expert> getAllExperts() {
        return expertRepository.findAll();
    }

    public Expert getExpertById(int expertId) {
        return expertRepository.findExpertById(expertId);
    }


}

package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Expert;
import com.fzoo.zoomanagementsystem.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;

    public List<Expert> getAllExperts() {
        return expertRepository.findAll();
    }

    public Expert getExpertById(int expertId) {
        return expertRepository.findExpertById(expertId);
    }
}

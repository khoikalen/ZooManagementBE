package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.FoodStorage;
import com.fzoo.zoomanagementsystem.repository.FoodStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodStorageService {
    @Autowired
    private FoodStorageRepository repository;

    public List<FoodStorage> getListFoodInStorage(String type) {
        return repository.findByType(type);
    }
}

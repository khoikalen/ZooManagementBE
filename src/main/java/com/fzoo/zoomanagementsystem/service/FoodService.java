package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FoodService {

    @Autowired
    private FoodRepository repository;

    Set<Food> setFood;
    public void addFood(Food food) {

        if(setFood==null){
            setFood=new HashSet<>();
        }
        setFood.add(food);


    }

    public Set<Food> getListFood(){
        return setFood;
    }

    public void clear(){
        setFood.clear();
    }

}

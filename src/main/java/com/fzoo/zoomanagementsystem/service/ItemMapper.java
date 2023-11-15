package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodStatisticResponse;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.FoodStorage;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.repository.FoodStorageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ItemMapper {
    private final ModelMapper modelMapper;
    @Autowired
    private FoodStorageRepository repo;
    public ItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FoodStatisticResponse convertToFoodStatisticResponse(Food food){
        FoodStatisticResponse f = new FoodStatisticResponse();
        float check = 0;
        FoodStorage foodStorage = repo.findById(food.getFoodStorageId()).orElseThrow();
        f.setName(food.getName());
        if(food.getMeasure().equals("gram")){
            check = food.getQuantity().floatValue()/1000;
            long price = (long) (check*foodStorage.getPrice());
            f.setPrice(price);
        }else{
            f.setPrice((long)food.getQuantity().intValue()*foodStorage.getPrice());
        }
        return  f;
    }


}

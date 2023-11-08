package com.fzoo.zoomanagementsystem.dto;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.Meal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class ItemMapper {
    private final ModelMapper modelMapper;

    public ItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public FoodStatisticResponse convertToFoodStatisticResponse(Food food){
        FoodStatisticResponse f = new FoodStatisticResponse();
        f.setName(food.getName());
        f.setQuantity(food.getQuantity().floatValue());
        return  f;
    }


}

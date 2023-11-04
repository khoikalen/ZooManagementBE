package com.fzoo.zoomanagementsystem.dto;

import com.fzoo.zoomanagementsystem.model.Food;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class ItemMapper {
    private final ModelMapper modelMapper;

    public ItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public FoodStatisticResponse convertToDto(Food food){
        FoodStatisticResponse f = new FoodStatisticResponse();
        f.setName(food.getName());
        f.setQuantity(food.getWeight());
        return  f;
    }
/*    public FoodStatisticResponse convertToDto(Food food) {
        return modelMapper.map(food, FoodStatisticResponse.class);
    }*/
}

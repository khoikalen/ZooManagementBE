package com.fzoo.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffMealResponse {
    private int cageId;
    private String name;
    private List<FoodInMealResponse> meal;
}

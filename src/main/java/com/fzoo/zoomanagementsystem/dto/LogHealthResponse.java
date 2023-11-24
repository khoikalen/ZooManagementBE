package com.fzoo.zoomanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogHealthResponse {
    String name;
    String species;
    String shortDescription;
    LocalDateTime LocalDateTime;
}

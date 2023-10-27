package com.fzoo.zoomanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketRequestDTO {
    private int quantityOfAdult;
    private int priceOfAdult;
    private int quantityOfChild;
    private int priceOfChild;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}

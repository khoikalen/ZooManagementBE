package com.fzoo.zoomanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketRequestDTO {
    @PositiveOrZero(message = "Can not input negative number in this field")
    private int quantityOfAdult;
    private int priceOfAdult;
    @PositiveOrZero(message = "Can not input negative number in this field")
    private int quantityOfChild;
    private int priceOfChild;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}

package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Ticket;
import com.fzoo.zoomanagementsystem.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("v1/ticket")
    public void checkoutTicket(@RequestBody Ticket request){
        ticketService.ticketCheckout(request);
    }
}

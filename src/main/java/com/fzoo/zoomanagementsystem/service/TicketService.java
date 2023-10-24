package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Ticket;
import com.fzoo.zoomanagementsystem.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public void ticketCheckout(Ticket request) {
        Ticket ticket = ticketRepository.findByPriceAndTypeAndDate(request.getPrice(), request.getType(), request.getDate());
        if (ticket != null) {
            if (request.getDate().isBefore(ticket.getDate())) {
                throw new IllegalStateException("There are some mismatch, please contact admin!");
            }
            ticket.setQuantity(ticket.getQuantity() + request.getQuantity());
            ticketRepository.save(ticket);
        } else {
            ticketRepository.save(request);
        }
    }

    public List<Ticket> getAllTicket() {
        List<Ticket> ticket = ticketRepository.findAll();
        if (ticket.isEmpty()) throw new IllegalStateException("There are no tickets !");
        return ticket;
    }
}

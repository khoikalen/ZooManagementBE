package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Ticket;
import com.fzoo.zoomanagementsystem.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final String SUNDAY = "SUNDAY";
    private final String SATURDAY = "SATURDAY";
    private final String ADULT = "ADULT";
    private final String CHILD = "CHILD";

    public void ticketCheckout(Ticket request) {
        Ticket ticket = ticketRepository.findByPriceAndTypeAndDate(request.getPrice(), request.getType(), request.getDate());
        LocalDate date = LocalDate.now();
        if (request.getDate().isBefore(date)) {
            throw new IllegalStateException("There are some mismatch, please contact admin!");
        }
        if (ticket != null) {
            ticket.setQuantity(ticket.getQuantity() + request.getQuantity());
            ticketRepository.save(ticket);
        } else {
            ticketRepository.save(request);
        }
    }

    public void ticketCheckoutV2(Ticket request) {
        Ticket ticket = ticketRepository.findByTypeAndDate(request.getType(), request.getDate());
        LocalDate date = LocalDate.now();
        String ticketDate = request.getDate().getDayOfWeek().toString();
        if (request.getDate().isBefore(date)) {
            throw new IllegalStateException("There are some mismatch, please contact admin!");
        }
        if (ticketDate.equals(SATURDAY) || ticketDate.equals(SUNDAY)) {
            if (request.getType().equals(ADULT)) {
                request.setPrice(70000);
            } else if (request.getType().equals(CHILD)) {
                request.setPrice(40000);
            }
        } else {
            if (request.getType().equals(ADULT)) {
                request.setPrice(50000);
            } else if (request.getType().equals(CHILD)) {
                request.setPrice(30000);
            }
        }
        if (ticket != null) {
            ticket.setQuantity(ticket.getQuantity() + request.getQuantity());
            ticketRepository.save(ticket);
        } else {
            ticketRepository.save(request);
        }
    }


    public List<Ticket> getAllTicket() {
        List<Ticket> ticket = ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        if (ticket.isEmpty()) throw new IllegalStateException("There are no tickets !");
        return ticket;
    }
}

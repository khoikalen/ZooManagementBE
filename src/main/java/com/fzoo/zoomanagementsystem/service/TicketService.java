package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.TicketRequestDTO;
import com.fzoo.zoomanagementsystem.exception.EmptyStringException;
import com.fzoo.zoomanagementsystem.model.Account;
import com.fzoo.zoomanagementsystem.model.Role;
import com.fzoo.zoomanagementsystem.model.Ticket;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
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
            ticket.setTotal(ticket.getPrice() * ticket.getQuantity());
            ticketRepository.save(ticket);
        } else {
            request.setTotal(request.getQuantity() * request.getPrice());
            ticketRepository.save(request);
        }
    }

    public void ticketCheckoutV3(TicketRequestDTO request) {
        Ticket ticketTypeChild = ticketRepository.findByTypeAndDate(CHILD, request.getDate());
        Ticket ticketTypeAdult = ticketRepository.findByTypeAndDate(ADULT, request.getDate());
        Account account = accountRepository.findAccountByRole(Role.ADMIN);
        LocalDate date = LocalDate.now();
        String ticketDate = request.getDate().getDayOfWeek().toString();
        if(request.getQuantityOfChild() == 0 && request.getQuantityOfAdult() == 0) throw new IllegalStateException("You have not purchased any ticket, please input quantity in the field!");
        if (request.getDate().isBefore(date)) {
            throw new IllegalStateException("Can not buy ticket previous of current day");
        }

        if (ticketDate.equals(SATURDAY) || ticketDate.equals(SUNDAY)) {
            request.setPriceOfAdult(70000);
            request.setPriceOfChild(40000);
        } else {
            request.setPriceOfAdult(50000);
            request.setPriceOfChild(30000);
        }

        if (ticketTypeAdult != null) {
            ticketTypeAdult.setQuantity(ticketTypeAdult.getQuantity() + request.getQuantityOfAdult());
            ticketTypeAdult.setTotal(ticketTypeAdult.getPrice() * ticketTypeAdult.getQuantity());
            ticketRepository.save(ticketTypeAdult);
        } else {
            ticketTypeAdult = new Ticket(0, ADULT, request.getPriceOfAdult(), request.getQuantityOfAdult(), request.getDate(), request.getQuantityOfAdult() * request.getPriceOfAdult(), account.getId());
            ticketRepository.save(ticketTypeAdult);
        }

        if (ticketTypeChild != null) {
            ticketTypeChild.setQuantity(ticketTypeChild.getQuantity() + request.getQuantityOfChild());
            ticketTypeChild.setTotal(ticketTypeChild.getPrice() * ticketTypeChild.getQuantity());
            ticketRepository.save(ticketTypeChild);
        } else {
            ticketTypeChild = new Ticket(1, CHILD, request.getPriceOfChild(), request.getQuantityOfChild(), request.getDate(), request.getQuantityOfChild() * request.getPriceOfChild(), account.getId());
            ticketRepository.save(ticketTypeChild);
        }
    }


    public List<Ticket> getAllTicket() {
        List<Ticket> ticket = ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        if (ticket.isEmpty()) throw new IllegalStateException("There are no tickets !");
        return ticket;
    }

}

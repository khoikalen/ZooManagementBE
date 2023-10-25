package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Ticket findByPriceAndTypeAndDate(int price, String type, LocalDate date);


    Ticket findByTypeAndDate(String type, LocalDate date);
}

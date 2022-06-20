package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.entities.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllByUserId(long userId);
}

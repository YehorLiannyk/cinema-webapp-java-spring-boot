package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.TicketDTO;

import java.util.Locale;

public interface TicketEmailService {
    void sentTicketViaEmail(TicketDTO ticketDTO, Locale locale, String email);
}

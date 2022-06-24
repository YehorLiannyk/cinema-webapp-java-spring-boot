package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.TicketDTO;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public interface TicketPDFService {
    ByteArrayOutputStream formPDFTicketToStream(TicketDTO ticket, Locale locale);
}

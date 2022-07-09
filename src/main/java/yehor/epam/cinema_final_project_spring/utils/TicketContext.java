package yehor.epam.cinema_final_project_spring.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.ComponentNotSetException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.CantSendTicketException;
import yehor.epam.cinema_final_project_spring.services.TicketPDFService;
import yehor.epam.cinema_final_project_spring.utils.ticketStrategy.SendTicketStrategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Context for realization Strategy pattern for ticket sending
 */
@Data
@Service
@Slf4j
public class TicketContext {
    //autowired
    private TicketPDFService ticketPDFService;
    // set
    private Locale locale;
    private SendTicketStrategy strategy;
    private TicketDTO ticketDTO;

    @Autowired
    public TicketContext(TicketPDFService ticketPDFService) {
        this.ticketPDFService = ticketPDFService;
    }

    /**
     * Send ticket with appropriate strategy
     */
    public void sendTicket() {
        isReady();
        try (ByteArrayOutputStream ticketPdfStream = ticketPDFService.formPdfTicketToStream(ticketDTO, locale)) {
            strategy.sendTicket(ticketPdfStream, locale);
        } catch (IOException e) {
            log.debug("Couldn't send ticket", e);
            throw new CantSendTicketException();
        }
    }

    /**
     * Check is Context has all required components to send ticket
     */
    public void isReady() {
        final boolean isReady = ticketPDFService != null && locale != null && strategy != null && ticketDTO != null;
        if (!isReady) {
            log.debug("ticketPDFService = {}, Locale = {}, strategy = {}, ticketDto = {}", ticketPDFService, locale, strategy, ticketDTO);
            throw new ComponentNotSetException();
        }
    }
}

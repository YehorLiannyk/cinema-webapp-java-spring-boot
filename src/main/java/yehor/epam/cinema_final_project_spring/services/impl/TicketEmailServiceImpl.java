package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.CantSendTicketEmailException;
import yehor.epam.cinema_final_project_spring.services.EmailService;
import yehor.epam.cinema_final_project_spring.services.TicketEmailService;
import yehor.epam.cinema_final_project_spring.services.TicketPDFService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Service
@Slf4j
public class TicketEmailServiceImpl implements TicketEmailService {
    private final EmailService emailService;
    private final TicketPDFService ticketPDFService;
    private final MessageSource messageSource;

    @Autowired
    public TicketEmailServiceImpl(EmailService emailService, TicketPDFService ticketPDFService, MessageSource messageSource) {
        this.emailService = emailService;
        this.ticketPDFService = ticketPDFService;
        this.messageSource = messageSource;
    }

    @Override
    public void sentTicketViaEmail(TicketDTO ticketDTO, Locale locale, String email) {
        try (ByteArrayOutputStream byteArrayOutputStream = ticketPDFService.formPDFTicketToStream(ticketDTO, locale)) {
            String subject = messageSource.getMessage("ticket.email.sendTicket.subject", null, locale);
            String text = messageSource.getMessage("ticket.email.sendTicket.text", null, locale);
            final String filename = Constants.DEF_TICKET_FILENAME + ".pdf";
            emailService.sendMsgWithAttachment(email, subject, text, byteArrayOutputStream, filename);
        } catch (IOException e) {
            throw new CantSendTicketEmailException(e);
        }
    }
}

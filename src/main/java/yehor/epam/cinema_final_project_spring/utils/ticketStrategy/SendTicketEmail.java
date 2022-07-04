package yehor.epam.cinema_final_project_spring.utils.ticketStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import yehor.epam.cinema_final_project_spring.exceptions.ComponentNotSetException;
import yehor.epam.cinema_final_project_spring.exceptions.email.CantSendTicketEmailException;
import yehor.epam.cinema_final_project_spring.services.EmailService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Strategy of sending ticket via email
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendTicketEmail implements SendTicketStrategy {
    private MessageSource messageSource;
    private EmailService emailService;
    private String userEmail;

    @Override
    public void sendTicket(ByteArrayOutputStream stream, Locale locale) {
        isReady();
        sentTicketViaEmail(stream, locale);
    }

    private void sentTicketViaEmail(ByteArrayOutputStream byteArrayOutputStream, Locale locale) {
        try {
            String subject = messageSource.getMessage("ticket.email.sendTicket.subject", null, locale);
            String text = messageSource.getMessage("ticket.email.sendTicket.text", null, locale);
            final String filename = Constants.DEF_TICKET_FILENAME + ".pdf";
            emailService.sendMsgWithAttachment(userEmail, subject, text, byteArrayOutputStream, filename);
        } catch (Exception e) {
            log.debug("Couldn't send ticket via email");
            throw new CantSendTicketEmailException(e);
        }
    }

    @Override
    public void isReady() {
        final boolean isReady = messageSource != null && emailService != null && userEmail != null;
        if (!isReady) {
            log.debug("Not all components were set: messageSource={}, emailService={}, userEmail={}", messageSource, emailService, userEmail);
            throw new ComponentNotSetException();
        }
    }
}

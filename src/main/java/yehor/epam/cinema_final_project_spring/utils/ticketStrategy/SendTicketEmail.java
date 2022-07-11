package yehor.epam.cinema_final_project_spring.utils.ticketStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import yehor.epam.cinema_final_project_spring.exceptions.ComponentNotSetException;
import yehor.epam.cinema_final_project_spring.exceptions.email.CantSendTicketEmailException;
import yehor.epam.cinema_final_project_spring.exceptions.email.EmailException;
import yehor.epam.cinema_final_project_spring.services.EmailService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.concurrent.*;

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
        sendTicketViaEmail(stream, locale);
    }

    private void sendTicketViaEmail(ByteArrayOutputStream byteArrayOutputStream, Locale locale) {
        try {
            String subject = messageSource.getMessage("ticket.email.sendTicket.subject", null, locale);
            String text = messageSource.getMessage("ticket.email.sendTicket.text", null, locale);
            final String filename = Constants.DEF_TICKET_FILENAME + ".pdf";
            sendMessage(byteArrayOutputStream, subject, text, filename);
        } catch (InterruptedException e) {
            log.debug("Couldn't create another thread to send ticket via email, thread throw InterruptedException", e);
            throw new CantSendTicketEmailException(e);
        } catch (EmailException e) {
            log.debug("Had problems with email service", e);
            throw new CantSendTicketEmailException(e);
        } catch (Exception e) {
            log.debug("Couldn't send ticket via email", e);
            throw new CantSendTicketEmailException(e);
        }
    }

    private void sendMessage(ByteArrayOutputStream byteArrayOutputStream, String subject, String text, String filename) throws InterruptedException, ExecutionException, EmailException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<Boolean> submit = executorService.submit(() -> tryToSendMessage(byteArrayOutputStream, subject, text, filename));
        final boolean isSent = submit.get(10, TimeUnit.SECONDS);
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            log.debug("Shutdown executorService");
        }
        if (!isSent) {
            log.debug("Email message wasn't sent, throw EmailException");
            throw new EmailException();
        }
    }

    private Boolean tryToSendMessage(ByteArrayOutputStream byteArrayOutputStream, String subject, String text, String filename) {
        try {
            emailService.sendMsgWithAttachment(userEmail, subject, text, byteArrayOutputStream, filename);
        } catch (Exception e) {
            log.error("Couldn't sent message via email", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
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

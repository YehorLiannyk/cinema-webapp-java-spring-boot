package yehor.epam.cinema_final_project_spring.utils.ticketStrategy;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Interface for send ticket strategy
 */
public interface SendTicketStrategy {
    /**
     * Send ticket
     *
     * @param stream ByteArrayOutputStream (PDF file)
     * @param locale Locale
     */
    void sendTicket(ByteArrayOutputStream stream, Locale locale);

    /**
     * Check if Strategy has all required components to send ticket
     */
    void isReady();
}

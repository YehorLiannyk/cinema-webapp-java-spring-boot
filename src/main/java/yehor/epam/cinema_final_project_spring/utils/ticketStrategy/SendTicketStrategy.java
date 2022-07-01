package yehor.epam.cinema_final_project_spring.utils.ticketStrategy;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public interface SendTicketStrategy {
    void sendTicket(ByteArrayOutputStream stream, Locale locale);

    void isReady();
}

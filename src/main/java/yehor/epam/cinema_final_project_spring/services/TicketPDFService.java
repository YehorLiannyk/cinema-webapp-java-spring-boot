package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.TicketDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

public interface TicketPDFService {
    /**
     * create ByteArrayOutputStream from ticket object and form it to PDF table
     *
     * @param ticket Ticket object
     * @return ByteArrayOutputStream
     */
    ByteArrayOutputStream formPdfTicketToStream(TicketDTO ticket, Locale locale);

    //void writePdfToResponse(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response);
}

package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.TicketDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

public interface TicketPDFService {
    ByteArrayOutputStream formPdfTicketToStream(TicketDTO ticket, Locale locale);

    void writePdfToResponse(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response);
}

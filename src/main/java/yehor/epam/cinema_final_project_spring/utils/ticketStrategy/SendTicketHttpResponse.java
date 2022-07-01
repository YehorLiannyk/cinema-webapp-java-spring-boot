package yehor.epam.cinema_final_project_spring.utils.ticketStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yehor.epam.cinema_final_project_spring.exceptions.ComponentNotSetException;
import yehor.epam.cinema_final_project_spring.exceptions.pdf.PdfException;
import yehor.epam.cinema_final_project_spring.exceptions.pdf.WritePdfToResponseException;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendTicketHttpResponse implements SendTicketStrategy {
    private HttpServletResponse response;

    @Override
    public void sendTicket(ByteArrayOutputStream stream, Locale locale) {
        isReady();
        writePdfToResponse(stream, locale);
    }

    @Override
    public void isReady() {
        final boolean isReady = response != null;
        if (!isReady) {
            log.debug("Not all components were set: HttpServletResponse={}", response);
            throw new ComponentNotSetException();
        }
    }

    private void writePdfToResponse(ByteArrayOutputStream byteArrayOutputStream, Locale locale) {
        try {
            writeByteArrayOutputStreamToResponse(byteArrayOutputStream, response);
        } catch (IOException e) {
            log.error("Couldn't write pdf ticket (byteArrayOutputStream) to HttpServletResponse", e);
            throw new WritePdfToResponseException(e);
        }
    }

    private void writeByteArrayOutputStreamToResponse(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response)
            throws IOException {
        try {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=" + Constants.DEF_TICKET_FILENAME);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            byteArrayOutputStream.writeTo(servletOutputStream);
        } catch (Exception e) {
            log.error("Handled error when trying to write PDF to output", e);
            throw new PdfException();
        }
    }
}

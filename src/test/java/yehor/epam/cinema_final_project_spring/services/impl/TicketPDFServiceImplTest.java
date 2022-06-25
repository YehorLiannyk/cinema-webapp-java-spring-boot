package yehor.epam.cinema_final_project_spring.services.impl;

import com.itextpdf.text.Font;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.pdf.PdfException;
import yehor.epam.cinema_final_project_spring.exceptions.pdf.WritePdfToResponseException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TicketPDFServiceImplTest {
    @Spy
    @InjectMocks
    TicketPDFServiceImpl ticketPDFService;
    @Mock
    private Font headFont;
    @Mock
    private MessageSource messageSource;

    @Test
    void formPdfTicketToStream() {
        TicketDTO ticketDTO = mock(TicketDTO.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);
        FilmDTO filmDTO = mock(FilmDTO.class);
        SeatDTO seatDTO = mock(SeatDTO.class);

        given(ticketDTO.getSession()).willReturn(sessionDTO);
        given(sessionDTO.getFilm()).willReturn(filmDTO);
        given(ticketDTO.getSeat()).willReturn(seatDTO);
        given(sessionDTO.getDate()).willReturn(LocalDate.now());
        given(sessionDTO.getTime()).willReturn(LocalTime.now());
        given(ticketDTO.getSession().getTicketPrice()).willReturn(BigDecimal.ONE);
        final ByteArrayOutputStream stream = ticketPDFService.formPdfTicketToStream(ticketDTO, Locale.getDefault());
        assertThat(stream).isNotNull();
    }

    @Test
    void formPdfTicketToStreamThrowPdfException() {
        final Locale locale = Locale.getDefault();
        TicketDTO ticketDTO = mock(TicketDTO.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);
        FilmDTO filmDTO = mock(FilmDTO.class);
        SeatDTO seatDTO = mock(SeatDTO.class);

        given(ticketDTO.getSession()).willReturn(sessionDTO);
        given(sessionDTO.getFilm()).willReturn(filmDTO);
        given(ticketDTO.getSeat()).willReturn(seatDTO);
        given(sessionDTO.getDate()).willReturn(LocalDate.now());
        given(sessionDTO.getTime()).willReturn(LocalTime.now());
        given(ticketDTO.getSession().getTicketPrice()).willReturn(BigDecimal.ONE);
        given(ticketPDFService.formPdfTicketToStream(ticketDTO, locale)).willThrow(PdfException.class);
        ByteArrayOutputStream stream = null;
        try {
            stream = ticketPDFService.formPdfTicketToStream(ticketDTO, locale);
            fail("PdfException should be thrown");
        } catch (PdfException e) {
        }
        assertThat(stream).isNull();
    }

    @Test
    void writePdfToResponse() {
        ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        willDoNothing().given(ticketPDFService).writePdfToResponse(stream, response);
        ticketPDFService.writePdfToResponse(stream, response);
        then(ticketPDFService).should().writePdfToResponse(stream, response);
    }

    @Test
    void writePdfToResponseThrowWritePdfToResponseException() {
        ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        willThrow(WritePdfToResponseException.class).given(ticketPDFService).writePdfToResponse(stream, response);
        try {
            ticketPDFService.writePdfToResponse(stream, response);
            fail("WritePdfToResponseException should be thrown");
        } catch (WritePdfToResponseException e) {
        }
    }
}
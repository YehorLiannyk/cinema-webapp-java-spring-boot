package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.email.CantSendTicketEmailException;
import yehor.epam.cinema_final_project_spring.services.EmailService;
import yehor.epam.cinema_final_project_spring.services.TicketPDFService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TicketEmailServiceImplTest {

    @Spy
    @InjectMocks
    private TicketEmailServiceImpl ticketEmailService;

    @Mock
    private EmailService emailService;
    @Mock
    private TicketPDFService ticketPDFService;
    @Mock
    private MessageSource messageSource;

    @Test
    void sentTicketViaEmail() {
        final TicketDTO ticketDTO = mock(TicketDTO.class);
        final Locale locale = Locale.getDefault();
        ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
        given(ticketPDFService.formPdfTicketToStream(ticketDTO, locale)).willReturn(stream);
        ticketEmailService.sentTicketViaEmail(ticketDTO, locale, "");
        then(ticketEmailService).should().sentTicketViaEmail(ticketDTO, locale, "");
    }

  /*  @Test
    void sentTicketViaEmailThrowCantSendTicketEmailException() throws IOException {
        final TicketDTO ticketDTO = mock(TicketDTO.class);
        final Locale locale = Locale.getDefault();
        ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
        //given(ticketPDFService.formPdfTicketToStream(ticketDTO, locale)).willThrow(IOException.class);

        //willDoNothing().willThrow(IOException.class).given(stream).close();
        willDoNothing().willCallRealMethod().given(stream).close();
        try {
            ticketEmailService.sentTicketViaEmail(ticketDTO, locale, "");
            fail("CantSendTicketEmailException should be thrown");
        } catch (CantSendTicketEmailException e) {
        }
        then(emailService).should(never()).sendMsgWithAttachment("", "", "", stream, "");
    }*/
}
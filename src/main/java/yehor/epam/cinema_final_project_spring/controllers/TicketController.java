package yehor.epam.cinema_final_project_spring.controllers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.PDFException;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.TicketEmailService;
import yehor.epam.cinema_final_project_spring.services.TicketPDFService;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketEmailService ticketEmailService;
    private final TicketPDFService ticketPDFService;
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService, TicketPDFService ticketPDFService, TicketEmailService ticketEmailService) {
        this.ticketService = ticketService;
        this.ticketPDFService = ticketPDFService;
        this.ticketEmailService = ticketEmailService;
    }

    @PostMapping
    public String createTickets(@SessionAttribute(name = "ticketList") List<TicketDTO> ticketList) {
        ticketService.save(ticketList);
        return "redirect:/tickets/order/success-payment";
    }

    @GetMapping("/order/success-payment")
    public String getSuccessPayPage() {
        return HtmlFileConstants.SUCCESS_PAYMENT_PAGE;
    }

    @GetMapping("/{id}/pdf")
    public void downloadPdfTicket(@PathVariable Long id, HttpServletResponse response, Locale locale) {
        final TicketDTO ticketDTO = ticketService.getById(id);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=" + "ticket.pdf");
        try (ByteArrayOutputStream byteArrayOutputStream = ticketPDFService.formPDFTicketToStream(ticketDTO, locale)) {
            writePDFToResponse(byteArrayOutputStream, response, ticketDTO, locale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/{id}/email")
    public String sendMailTicket(@PathVariable Long id, Locale locale, @AuthenticationPrincipal CustomUserDetails userDetails) {
        final TicketDTO ticketDTO = ticketService.getById(id);
        ticketEmailService.sentTicketViaEmail(ticketDTO, locale, userDetails.getUsername());
        return "redirect:/";
    }


    /**
     * Call form PDF method and then write received ByteArrayOutputStream to servletOutputStream;
     *
     * @param response HttpServletResponse
     * @param ticket   Ticket for PDF
     * @SneakyThrows - There is @SneakyThrows because IOException from ByteArrayOutputStream is only one checked exception here,
     * don't want to add try-catch block. It will handle with general exception handler
     */
    @SneakyThrows
    private void writePDFToResponse(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, TicketDTO ticket, Locale locale) {
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            byteArrayOutputStream.writeTo(servletOutputStream);
        } catch (Exception e) {
            log.error("Handled error when trying to write PDF to output", e);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            throw new PDFException();
        }
    }
}

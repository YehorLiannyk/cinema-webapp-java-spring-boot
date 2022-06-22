package yehor.epam.cinema_final_project_spring.controllers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.PDFException;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.utils.TicketPDFService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketPDFService ticketPDFService;

    @Autowired
    public TicketController(TicketService ticketService, TicketPDFService ticketPDFService) {
        this.ticketService = ticketService;
        this.ticketPDFService = ticketPDFService;
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
        formAndWritePDF(response, ticketDTO, locale);
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
    private void formAndWritePDF(HttpServletResponse response, TicketDTO ticket, Locale locale) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = ticketPDFService.formPDFTicket(ticket, locale);
            final ServletOutputStream servletOutputStream = response.getOutputStream();
            byteArrayOutputStream.writeTo(servletOutputStream);
        } catch (Exception e) {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
            }
            log.error("Handled error when trying to write PDF to output");
            throw new PDFException();
        }
    }
}

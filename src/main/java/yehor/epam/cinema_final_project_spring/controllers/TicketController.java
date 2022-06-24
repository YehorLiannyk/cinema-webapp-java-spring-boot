package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.TicketEmailService;
import yehor.epam.cinema_final_project_spring.services.TicketPDFService;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
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
    public void getPdfTicket(@PathVariable Long id, HttpServletResponse response, Locale locale) {
        final TicketDTO ticketDTO = ticketService.getById(id);
        ByteArrayOutputStream ticketPdfStream = ticketPDFService.formPdfTicketToStream(ticketDTO, locale);
        ticketPDFService.writePdfToResponse(ticketPdfStream, response);
    }

    @GetMapping("/{id}/email")
    public String sendMailTicket(@PathVariable Long id, Locale locale, @AuthenticationPrincipal CustomUserDetails userDetails) {
        final TicketDTO ticketDTO = ticketService.getById(id);
        ticketEmailService.sentTicketViaEmail(ticketDTO, locale, userDetails.getUsername());
        return "redirect:/";
    }
}

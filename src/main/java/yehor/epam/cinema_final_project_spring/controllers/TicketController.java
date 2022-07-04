package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.EmailService;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.utils.ticketStrategy.SendTicketEmail;
import yehor.epam.cinema_final_project_spring.utils.ticketStrategy.SendTicketHttpResponse;
import yehor.epam.cinema_final_project_spring.utils.ticketStrategy.SendTicketStrategy;
import yehor.epam.cinema_final_project_spring.utils.TicketContext;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final EmailService emailService;
    private final TicketService ticketService;
    private final TicketContext ticketContext;
    private final MessageSource messageSource;

    @Autowired
    public TicketController(EmailService emailService, TicketService ticketService, TicketContext ticketContext, MessageSource messageSource) {
        this.emailService = emailService;
        this.ticketService = ticketService;
        this.ticketContext = ticketContext;
        this.messageSource = messageSource;
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
        SendTicketStrategy strategy = new SendTicketHttpResponse(response);
        ticketContext.setTicketDTO(ticketDTO);
        ticketContext.setStrategy(strategy);
        ticketContext.setLocale(locale);
        ticketContext.sendTicket();
    }

    @GetMapping("/{id}/email")
    public String sendMailTicket(@PathVariable Long id, Locale locale, @AuthenticationPrincipal CustomUserDetails userDetails) {
        final TicketDTO ticketDTO = ticketService.getById(id);
        SendTicketStrategy strategy = new SendTicketEmail(messageSource, emailService, userDetails.getUsername());
        ticketContext.setTicketDTO(ticketDTO);
        ticketContext.setStrategy(strategy);
        ticketContext.setLocale(locale);
        ticketContext.sendTicket();
        return "redirect:/users/me/tickets";
    }
}

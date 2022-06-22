package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.entities.Ticket;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;

    @Autowired
    public UserController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping(value = "/me/profile")
    public String userProfilePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        final Long userId = userService.getUserIdByEmail(userDetails.getUsername());
        final List<TicketDTO> ticketList = ticketService.getAllByUserId(userId);
        log.debug("User's ticket list: " + ticketList);
        model.addAttribute("ticketList", ticketList);
        return HtmlFileConstants.USER_PROFILE_PAGE;
    }
}

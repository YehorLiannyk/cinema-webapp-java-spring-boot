package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.PaginationService;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;
    private final PaginationService paginationService;

    @Autowired
    public UserController(UserService userService, TicketService ticketService, PaginationService paginationService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.paginationService = paginationService;
    }

    @GetMapping(value = "/me/tickets")
    public String getUserTickets(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
                                 @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = DEF_PAGING_SIZE_STR) int size,
                                 Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        final Long userId = userService.getUserIdByEmail(userDetails.getUsername());
        final Page<TicketDTO> ticketPage = ticketService.getAllByUserIdPage(page - 1, size, userId);
        paginationService.checkPaginatable(ticketPage.getTotalPages(), page, size);
        List<TicketDTO> ticketList = ticketPage.getContent();
        model.addAttribute("ticketList", ticketList);
        model.addAttribute(PAGE_AMOUNT_PARAM, ticketPage.getTotalPages());
        return HtmlFileConstants.USER_PROFILE_PAGE;
    }
}

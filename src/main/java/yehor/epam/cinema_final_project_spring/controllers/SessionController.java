package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatWasNotPickedException;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.*;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.ORDER_PAGE;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.SESSIONS_PAGE;

@Controller
@Slf4j
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;
    private final PaginationService paginationService;
    private final TicketService ticketService;
    private final UserService userService;

    @Autowired
    public SessionController(SessionService sessionService, PaginationService paginationService, TicketService ticketService, UserService userService) {
        this.sessionService = sessionService;
        this.paginationService = paginationService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping
    public String getSessions(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
                              @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = DEF_PAGING_SIZE_STR) int size,
                              @RequestParam(name = FILTER_SHOW_PARAM, required = false, defaultValue = FILTER_SHOW_ALL) String filter,
                              @RequestParam(name = SORT_BY_PARAM, required = false, defaultValue = SORT_BY_DATETIME) String sort,
                              @RequestParam(name = SORT_METHOD_PARAM, required = false, defaultValue = SORT_METHOD_ASC) String method,
                              Model model) {
        final Page<SessionDTO> sessionPage = sessionService.getAll(page - 1, size, filter, sort, method);
        paginationService.checkPaginatable(sessionPage.getTotalPages(), page, size);
        final List<SessionDTO> sessionList = sessionPage.getContent();
        model.addAttribute(PAGE_AMOUNT_PARAM, sessionPage.getTotalPages());
        model.addAttribute("allSessionList", sessionList);
        return SESSIONS_PAGE;
    }


    @GetMapping("/{id}")
    public String getSessionPage(@PathVariable Long id, Model model) {
        final SessionDTO sessionDTO = sessionService.getById(id);
        final Map<SeatDTO, Boolean> freeAndReservedSeatMap = sessionService.getFreeAndReservedSeatMap(id);
        model.addAttribute("theSession", sessionDTO);
        model.addAttribute("freeAndReservedSeatMap", freeAndReservedSeatMap);
        return HtmlFileConstants.SESSION_PAGE;
    }

    @PostMapping("/{id}/order")
    public String formOrderPage(@PathVariable(name = "id") Long sessionId, @AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestParam(name = "seat", required = false) List<SeatDTO> seatDTOList,
                                HttpSession session, Model model) {
        if (seatDTOList == null || seatDTOList.isEmpty()) {
            log.debug("User didn't pick any seat for ticket");
            throw new SeatWasNotPickedException();
        }
        final Long userId = userService.getUserIdFromAuthentication(userDetails);
        final List<TicketDTO> ticketList = ticketService.formTicketList(seatDTOList, sessionId, userId);
        final BigDecimal totalCost = ticketService.countTotalCost(ticketList);
        session.setAttribute("ticketList", ticketList);
        model.addAttribute("totalCost", totalCost);
        return ORDER_PAGE;
    }

}

package yehor.epam.cinema_final_project_spring.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.PaginationService;
import yehor.epam.cinema_final_project_spring.services.SessionService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.*;

@Controller
@Slf4j
@RequestMapping("/admins/sessions")
public class AdminSessionController {
    private final FilmService filmService;
    private final SessionService sessionService;
    private final PaginationService paginationService;

    @Autowired
    public AdminSessionController(FilmService filmService, SessionService sessionService, PaginationService paginationService) {
        this.filmService = filmService;
        this.sessionService = sessionService;
        this.paginationService = paginationService;
    }

    @GetMapping("/new")
    public String addSessionPage(Model model) {
        model.addAttribute("session", new SessionDTO());
        model.addAttribute("allFilmList", filmService.getAll());
        return ADMIN_ADD_SESSION_PAGE;
    }

    @PostMapping
    public String createSession(@ModelAttribute(name = "session") @Valid SessionDTO sessionDTO, BindingResult bindingResult,
                                Model model) {
        log.debug("Received session: " + sessionDTO);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allFilmList", filmService.getAll());
            return ADMIN_ADD_SESSION_PAGE;
        }
        sessionService.save(sessionDTO);
        return "redirect:/admins/sessions";
    }

    @GetMapping
    public String getAdminSessionPage(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
                                    @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = DEF_PAGING_SIZE_STR) int size,
                                    Model model) {
        final Page<SessionDTO> sessionPage = sessionService.getAllSortedByIdAndPaginated(page - 1, DEFAULT_PAGING_SIZE);
        paginationService.checkPaginatable(sessionPage.getTotalPages(), page, size);
        final List<SessionDTO> sessionList = sessionPage.getContent();
        model.addAttribute(PAGE_AMOUNT_PARAM, sessionPage.getTotalPages());
        model.addAttribute("allSessionList", sessionList);
        log.debug("allSessionList: " + sessionList);
        return ADMIN_SESSIONS_SETTING_PAGE;
    }

    @DeleteMapping("/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.deleteById(id);
        return "redirect:/admins/sessions";
    }

    @GetMapping("/{id}")
    public String getSessionPage(@PathVariable Long id, Model model) {
        final SessionDTO sessionDTO = sessionService.getById(id);
        final Map<SeatDTO, Boolean> freeAndReservedSeatMap = sessionService.getFreeAndReservedSeatMap(id);
        model.addAttribute("theSession", sessionDTO);
        model.addAttribute("freeAndReservedSeatMap", freeAndReservedSeatMap);
        log.debug("theSession = " + sessionDTO);
        log.debug("freeAndReservedSeatMap.size = " + freeAndReservedSeatMap.size());
        return ADMIN_SESSION_PAGE;
    }
}

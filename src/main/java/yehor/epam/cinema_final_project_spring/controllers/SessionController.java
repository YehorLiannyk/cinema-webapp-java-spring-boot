package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.PaginationService;
import yehor.epam.cinema_final_project_spring.services.SessionService;

import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.SESSIONS_PAGE;

@Slf4j
@Controller
@RequestMapping("/sessions")
public class SessionController {
    private final FilmService filmService;
    private final SessionService sessionService;
    private final PaginationService paginationService;

    @Autowired
    public SessionController(FilmService filmService, SessionService sessionService, PaginationService paginationService) {
        this.filmService = filmService;
        this.sessionService = sessionService;
        this.paginationService = paginationService;
    }

    @GetMapping
    public String getSessions(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
                              @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = DEF_PAGING_SIZE_STR) int size,
                              @RequestParam(name = FILTER_SHOW_PARAM, required = false, defaultValue = FILTER_SHOW_ALL) String filter,
                              @RequestParam(name = SORT_BY_PARAM, required = false, defaultValue = SORT_BY_DATETIME) String sort,
                              @RequestParam(name = SORT_METHOD_PARAM, required = false, defaultValue = SORT_METHOD_ASC) String method,
                              Model model) {
        final Page<SessionDTO> sessionPage = sessionService.getAll(page - 1, size, filter, sort, method);
        log.debug("page = " + page + " sessionPage.getContent(): " + sessionPage.getContent());
        log.debug("page = " + page + ", size = " + size + ", filter = " + filter + ", sort = " + sort + ", method = " + method);
        log.debug("getAllSession: " + sessionService.getAll());
        log.debug("getAllSession size: " + sessionService.getAll().size());
        paginationService.checkPaginatable(sessionPage.getTotalPages(), page, size);
        final List<SessionDTO> sessionList = sessionPage.getContent();
        model.addAttribute(PAGE_AMOUNT_PARAM, sessionPage.getTotalPages());
        model.addAttribute("allSessionList", sessionList);
        log.debug("allSessionList: " + sessionList);
        return SESSIONS_PAGE;
    }


   /* @GetMapping("/{id}")
    public String getFilmPage(@PathVariable Long id, Model model) {
        final FilmDTO filmDTO = filmService.getById(id);
        model.addAttribute("film", filmDTO);
        return HtmlFileConstants.SESSION_PAGE;
    }*/
}

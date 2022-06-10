package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.PaginationService;

import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@Controller
public class MainPageController {
    private final FilmService filmService;
    private final PaginationService paginationService;

    @Autowired
    public MainPageController(FilmService filmService, PaginationService paginationService) {
        this.filmService = filmService;
        this.paginationService = paginationService;
    }

    @GetMapping(value = {"/", "/main"})
    public String mainPageWithPagination(@ModelAttribute(name = PAGE_NUMBER_PARAM) Integer page, Model model) {
        final List<Film> filmList = filmService.getAllSortedByIdAndPaginated(page, DEFAULT_PAGING_SIZE);
        model.addAttribute("filmList", filmList);
        return HtmlFileConstants.MAIN_PAGE;
    }

    @ModelAttribute
    public void addAttributes(@RequestParam(name = PAGE_NUMBER_PARAM, defaultValue = "0") int pageNo, Model model) {
        final Long totalAmount = filmService.getTotalAmount();
        int pageAmount = paginationService.getPageAmount(totalAmount, DEFAULT_PAGING_SIZE);
        model.addAttribute(PAGE_NUMBER_PARAM, pageNo);
        model.addAttribute(PAGE_AMOUNT_PARAM, pageAmount);
    }
}

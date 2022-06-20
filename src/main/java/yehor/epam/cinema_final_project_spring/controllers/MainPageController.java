package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.exceptions.PaginationException;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@Slf4j
@Controller
public class MainPageController {
    private final FilmService filmService;

    @Autowired
    public MainPageController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = {"/", "/main"})
    public String getMainPageWithPagination(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
                                            @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = DEF_PAGING_SIZE_STR) int size,
                                            Model model) {
        final Page<FilmDTO> filmPage = filmService.getAllSortedByIdAndPaginated(page - 1, DEFAULT_PAGING_SIZE);
        if (page > filmPage.getTotalPages()) {
            log.debug("Page amount is bigger than total page amount. Params: pageNo = " + page + ", size = " + size);
            throw new PaginationException();
        }
        final List<FilmDTO> filmList = filmPage.getContent();
        model.addAttribute(PAGE_AMOUNT_PARAM, filmPage.getTotalPages());
        model.addAttribute("filmList", filmList);
        return HtmlFileConstants.MAIN_PAGE;
    }
}

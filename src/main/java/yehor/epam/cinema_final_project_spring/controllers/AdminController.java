package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.exceptions.PaginationException;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.GenreService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

import javax.validation.Valid;
import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.ADMIN_ADD_FILM_PAGE;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.ADMIN_FILMS_SETTING_PAGE;

@Controller
@Slf4j
@RequestMapping("/admins")
public class AdminController {
    private final GenreService genreService;
    private final FilmService filmService;

    @Autowired
    public AdminController(GenreService genreService, FilmService filmService) {
        this.genreService = genreService;
        this.filmService = filmService;
    }

    @GetMapping("/films/new")
    public String addFilmPage(Model model) {
        model.addAttribute("film", new FilmDTO());
        model.addAttribute("allGenreList", genreService.getAll());
        return ADMIN_ADD_FILM_PAGE;
    }

    @PostMapping("/films")
    public String createFilm(@ModelAttribute(name = "film") @Valid FilmDTO filmDTO, BindingResult bindingResult,
                             @RequestParam(name = "genreList", required = false) List<GenreDTO> genreList, Model model) {
        if (genreList == null || genreList.isEmpty()) {
            log.debug("Genre list is null or empty, add error to bindingResult");
            model.addAttribute("genreListIsEmpty", true);
            bindingResult.addError(new FieldError("film", "genreList", "Can't be empty"));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("allGenreList", genreService.getAll());
            return ADMIN_ADD_FILM_PAGE;
        }
        filmDTO.setGenreList(genreList);
        filmService.save(filmDTO);
        return "redirect:/" + ADMIN_FILMS_SETTING_PAGE;
    }

    @GetMapping("/films")
    public String getAdminFilmsPage(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
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
        return ADMIN_FILMS_SETTING_PAGE;
    }

    /*@DeleteMapping("/films/{id}")
    public String deleteFilm(@PathVariable Integer id) {

        return HtmlFileConstants.MAIN_PAGE;
    }*/


}

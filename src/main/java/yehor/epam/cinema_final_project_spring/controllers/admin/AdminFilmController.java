package yehor.epam.cinema_final_project_spring.controllers.admin;

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
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.GenreService;
import yehor.epam.cinema_final_project_spring.services.PaginationService;

import javax.validation.Valid;
import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;
import static yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants.*;

@Controller
@Slf4j
@RequestMapping("/admins/films")
public class AdminFilmController {
    private final GenreService genreService;
    private final FilmService filmService;
    private final PaginationService paginationService;

    @Autowired
    public AdminFilmController(GenreService genreService, FilmService filmService, PaginationService paginationService) {
        this.genreService = genreService;
        this.filmService = filmService;
        this.paginationService = paginationService;
    }

    @GetMapping("/new")
    public String addFilmPage(Model model) {
        model.addAttribute("film", new FilmDTO());
        model.addAttribute("allGenreList", genreService.getAll());
        return ADMIN_ADD_FILM_PAGE;
    }

    @PostMapping
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
        return "redirect:/admins/films";
    }

    @GetMapping
    public String getAdminFilmsPage(@RequestParam(name = PAGE_NO_PARAM, required = false, defaultValue = "1") int page,
                                    @RequestParam(name = PAGE_SIZE_PARAM, required = false, defaultValue = DEF_PAGING_SIZE_STR) int size,
                                    Model model) {
        final Page<FilmDTO> filmPage = filmService.getAllSortedByIdAndPaginated(page - 1, DEFAULT_PAGING_SIZE);
        paginationService.checkPaginatable(filmPage.getTotalPages(), page, size);
        final List<FilmDTO> filmList = filmPage.getContent();
        model.addAttribute(PAGE_AMOUNT_PARAM, filmPage.getTotalPages());
        model.addAttribute("filmList", filmList);
        return ADMIN_FILMS_SETTING_PAGE;
    }

    @DeleteMapping("/{id}")
    public String deleteFilm(@PathVariable Long id) {
        filmService.deleteById(id);
        return "redirect:/admins/films";
    }


}

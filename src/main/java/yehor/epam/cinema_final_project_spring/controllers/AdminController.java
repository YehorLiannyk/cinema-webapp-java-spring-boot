package yehor.epam.cinema_final_project_spring.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.GenreService;

import javax.validation.Valid;
import java.util.List;

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
            log.debug("bindingResult.hasErrors() true");
            model.addAttribute("allGenreList", genreService.getAll());
            return ADMIN_ADD_FILM_PAGE;
        }
        filmDTO.setGenreList(genreList);
        log.debug("Received film: " + filmDTO.toString());
        filmService.save(filmDTO);
        return "redirect:/" + ADMIN_FILMS_SETTING_PAGE;
    }
}

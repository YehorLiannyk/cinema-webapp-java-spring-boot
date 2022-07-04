package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.utils.constants.HtmlFileConstants;

@Controller
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public String getFilmPage(@PathVariable Long id, Model model) {
        final FilmDTO filmDTO = filmService.getById(id);
        model.addAttribute("film", filmDTO);
        return HtmlFileConstants.FILM_PAGE;
    }
}

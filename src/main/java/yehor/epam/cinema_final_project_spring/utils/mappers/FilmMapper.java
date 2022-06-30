package yehor.epam.cinema_final_project_spring.utils.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FilmMapper {
    private final MapperDtoFacade facade;

    public FilmMapper(MapperDtoFacade facade) {
        this.facade = facade;
    }

    public FilmDTO fromFilm(Film film) {
        Long durationInMin = film.getDuration().toMinutes();
        log.debug("GenreList before mapper: " + film.getGenreList());
        final List<GenreDTO> genreDTOList = facade.getGenreMapper().fromGenreList(film.getGenreList());
        log.debug("GenreList after mapper: " + genreDTOList);
        return new FilmDTO(
                film.getId(),
                film.getName(),
                film.getDescription(),
                durationInMin,
                genreDTOList,
                film.getPosterUrl()
        );
    }

    public Film toFilm(FilmDTO filmDTO) {
        Duration duration = null;
        if (filmDTO.getDuration() != null)
            duration = Duration.ofMinutes(filmDTO.getDuration());
        List<Genre> genreList = facade.getGenreMapper().toGenreList(filmDTO.getGenreList());

        Film film = new Film();
        film.setId(filmDTO.getId());
        film.setName(filmDTO.getName());
        film.setDescription(filmDTO.getDescription());
        film.setDuration(duration);
        film.setGenreList(genreList);
        film.setPosterUrl(filmDTO.getPosterUrl());
        return film;
    }

    public List<FilmDTO> fromFilmList(List<Film> filmList) {
        List<FilmDTO> list = new ArrayList<>();
        if (filmList != null)
            filmList.forEach(film -> list.add(fromFilm(film)));
        return list;
    }

    public List<Film> toFilmList(List<FilmDTO> filmDTOS) {
        List<Film> list = new ArrayList<>();
        if (filmDTOS != null)
            filmDTOS.forEach(film -> list.add(toFilm(film)));
        return list;
    }

    public Page<FilmDTO> fromFilmPage(Page<Film> filmPage) {
        final List<FilmDTO> filmDTOS = fromFilmList(filmPage.getContent());
        return new PageImpl<>(filmDTOS, filmPage.getPageable(), filmPage.getTotalElements());
    }


}

package yehor.epam.cinema_final_project_spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.dto.UserDTO;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.entities.User;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MapperDTO {
    public User toUser(UserDTO userDTO) {
        throw new IllegalStateException("This method is not realized yet");
    }

    public User toUser(UserSignUpDTO userDTO) {
        final User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserRole(userDTO.getUserRole());
        user.setNotification(userDTO.getNotification());
        return user;
    }

    public GenreDTO fromGenre(Genre genre) {
        return new GenreDTO(genre.getId(), genre.getName());
    }

    public Genre toGenre(GenreDTO genreDTO) {
        return new Genre(genreDTO.getId(), genreDTO.getName());
    }

    public List<GenreDTO> fromGenreList(List<Genre> genreList) {
        List<GenreDTO> list = new ArrayList<>();
        genreList.forEach(genre -> list.add(fromGenre(genre)));
        return list;
    }

    public List<Genre> toGenreList(List<GenreDTO> genreDTOList) {
        List<Genre> list = new ArrayList<>();
        genreDTOList.forEach(genre -> list.add(toGenre(genre)));
        return list;
    }

    public FilmDTO fromFilm(Film film) {
        Long durationInMin = film.getDurationInMinutes();
        final List<GenreDTO> genreDTOList = fromGenreList(film.getGenreList());
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
        log.debug("Entry to mapper toFilm(FilmDTO filmDTO) method");
        Duration duration = Duration.ofMinutes(filmDTO.getDuration());
        List<Genre> genreList = toGenreList(filmDTO.getGenreList());
        return new Film(
                filmDTO.getId(),
                filmDTO.getName(),
                filmDTO.getDescription(),
                duration,
                genreList,
                filmDTO.getPosterUrl()
        );

    }

    public List<FilmDTO> froFilmList(List<Film> filmList) {
        List<FilmDTO> list = new ArrayList<>();
        filmList.forEach(film -> list.add(fromFilm(film)));
        return list;
    }

    public List<Film> toFilmList(List<FilmDTO> filmDTOS) {
        List<Film> list = new ArrayList<>();
        filmDTOS.forEach(film -> list.add(toFilm(film)));
        return list;
    }

    public Page<FilmDTO> fromFilmPage(Page<Film> filmPage) {
        final List<FilmDTO> filmDTOS = froFilmList(filmPage.getContent());
        return new PageImpl<>(filmDTOS, filmPage.getPageable(), filmPage.getTotalElements());
    }
}

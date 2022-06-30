package yehor.epam.cinema_final_project_spring.utils.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.time.Duration;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FilmMapperTest {

    @Spy
    @InjectMocks
    private FilmMapper filmMapper;

    @Mock
    private MapperDtoFacade facade;

    @Mock
    private GenreMapper genreMapper;

    @Test
    void fromFilm() {
        Film film = new Film();
        final Duration duration = Duration.ofMinutes(100);
        List<Genre> genreList = mock(List.class);
        List<GenreDTO> genreDtoList = mock(List.class);
        film.setId(1L);
        film.setName("name");
        film.setDescription("desc");
        film.setPosterUrl("url");
        film.setDuration(duration);
        film.setGenreList(genreList);

        given(facade.getGenreMapper()).willReturn(genreMapper);
        given(genreMapper.fromGenreList(genreList)).willReturn(genreDtoList);
        final FilmDTO filmDTO = filmMapper.fromFilm(film);

        Assertions.assertEquals(film.getId(), filmDTO.getId());
        Assertions.assertEquals(film.getName(), filmDTO.getName());
        Assertions.assertEquals(film.getDescription(), filmDTO.getDescription());
        Assertions.assertEquals(film.getPosterUrl(), filmDTO.getPosterUrl());
        Assertions.assertEquals(duration.toMinutes(), filmDTO.getDuration());
        Assertions.assertEquals(film.getGenreList().size(), filmDTO.getGenreList().size());
    }

    @Test
    void toFilm() {
        FilmDTO filmDto = new FilmDTO();
        List<Genre> genreList = mock(List.class);
        List<GenreDTO> genreDtoList = mock(List.class);
        filmDto.setId(1L);
        filmDto.setName("name");
        filmDto.setDescription("desc");
        filmDto.setPosterUrl("url");
        filmDto.setDuration(100L);
        filmDto.setGenreList(genreDtoList);

        given(facade.getGenreMapper()).willReturn(genreMapper);
        final Film film = filmMapper.toFilm(filmDto);

        Assertions.assertEquals(filmDto.getId(), film.getId());
        Assertions.assertEquals(filmDto.getName(), film.getName());
        Assertions.assertEquals(filmDto.getDescription(), film.getDescription());
        Assertions.assertEquals(filmDto.getPosterUrl(), film.getPosterUrl());
        Assertions.assertEquals(Duration.ofMinutes(100L), film.getDuration());
        Assertions.assertEquals(filmDto.getGenreList().size(), film.getGenreList().size());
    }

    @Test
    void fromFilmList() {
        List<Film> filmList = mock(List.class);
        final List<FilmDTO> filmDTOS = filmMapper.fromFilmList(filmList);
        Assertions.assertEquals(filmList.size(), filmDTOS.size());
    }

    @Test
    void fromFilmPage() {
        Page<Film> filmPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        given(filmPage.getPageable()).willReturn(pageable);
        final Page<FilmDTO> filmDTOS = filmMapper.fromFilmPage(filmPage);
        Assertions.assertEquals(filmPage.getTotalElements(), filmDTOS.getTotalElements());
    }
}
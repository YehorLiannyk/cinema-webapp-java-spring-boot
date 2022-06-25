package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.FilmRepository;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {
    @Spy
    @InjectMocks
    private FilmServiceImpl filmService;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private MapperDTO mapperDTO;

    @Test
    void getAllSortedByIdAndPaginated() {
        final Pageable pageable = PageRequest.of(1, 1);
        given(filmRepository.findAllByOrderByIdDesc(pageable)).willReturn(Page.empty());
        final int pageSize = pageable.getPageSize();
        final int pageNumber = pageable.getPageNumber();
        given(filmService.getAllSortedByIdAndPaginated(pageNumber, pageSize)).willReturn(Page.empty());
        final Page<FilmDTO> filmPage = filmService.getAllSortedByIdAndPaginated(pageNumber, pageSize);
        assertThat(filmPage).isNotNull();
    }

    @Test
    void save() {
        Film film = mock(Film.class);
        FilmDTO filmDTO = mock(FilmDTO.class);
        given(filmRepository.save(film)).willReturn(film);
        given(mapperDTO.toFilm(filmDTO)).willReturn(film);
        filmService.save(filmDTO);
        then(filmService).should(times(1)).save(filmDTO);
    }

    @Test
    void getById() {
        Film film = mock(Film.class);
        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
        given(mapperDTO.fromFilm(film)).willReturn(mock(FilmDTO.class));
        final FilmDTO filmDTO = filmService.getById(1L);
        assertThat(filmDTO).isNotNull();
    }

    @Test
    void deleteById() {
        willDoNothing().given(filmRepository).deleteById(1L);
        given(filmRepository.existsById(1L)).willReturn(true);
        filmService.deleteById(1L);
        then(filmService).should(times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowException() {
        given(filmRepository.existsById(1L)).willReturn(false);
        try {
            filmService.deleteById(1L);
            fail("Should throw FilmNotFoundException");
        } catch (FilmNotFoundException e) {
        }
        then(filmRepository).should(never()).deleteById(1L);
    }

    @Test
    void getAll() {
        List<Film> filmList = List.of(new Film());
        given(filmRepository.findAll()).willReturn(filmList);
        filmService.getAll();
        then(filmService).should(times(1)).getAll();
        assertThat(filmService.getAll()).isNotNull();
    }

    @Test
    void getAllAndThrowFilmListIsEmptyException() {
        List<FilmDTO> list = null;
        try {
            list = filmService.getAll();
            fail("FilmListIsEmptyException should be thrown");
        } catch (FilmListIsEmptyException e) {
        }
        assertThat(list).isNull();
    }
}
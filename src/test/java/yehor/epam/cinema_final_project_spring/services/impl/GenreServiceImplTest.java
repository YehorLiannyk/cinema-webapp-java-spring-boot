package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreListIsEmptyException;
import yehor.epam.cinema_final_project_spring.repositories.GenreRepository;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;
import yehor.epam.cinema_final_project_spring.utils.mappers.GenreMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {
    @Spy
    @InjectMocks
    private GenreServiceImpl genreService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private MapperDtoFacade mapperDTO;

    @Test
    void getAll() {
        List<Genre> genreList = List.of(new Genre());
        GenreMapper genreMapper = mock(GenreMapper.class);
        given(mapperDTO.getGenreMapper()).willReturn(genreMapper);
        given(genreRepository.findAll()).willReturn(genreList);
        genreService.getAll();
        then(genreService).should(times(1)).getAll();
        assertThat(genreService.getAll()).isNotNull();
    }

    @Test
    void getAllAndThrowGenreListIsEmptyException() {
        List<GenreDTO> list = null;
        try {
            list = genreService.getAll();
            fail("GenreListIsEmptyException should be thrown");
        } catch (GenreListIsEmptyException e) {
        }
        assertThat(list).isNull();
    }

    @Test
    void getAllByIdList() {
        List<Long> idList = List.of(1L);
        List<Genre> genreList = List.of(new Genre());
        GenreMapper genreMapper = mock(GenreMapper.class);
        given(mapperDTO.getGenreMapper()).willReturn(genreMapper);
        given(genreRepository.findByIdIn(idList)).willReturn(genreList);
        genreService.getAllByIdList(idList);
        then(genreService).should(times(1)).getAllByIdList(idList);
        assertThat(genreService.getAllByIdList(idList)).isNotNull();
    }

    @Test
    void getAllByIdListThrowGenreListIsEmptyException() {
        List<Long> idList = List.of();
        List<Genre> genreList = List.of();
        given(genreRepository.findByIdIn(idList)).willReturn(genreList);
        List<GenreDTO> allByIdList = null;
        try {
            allByIdList = genreService.getAllByIdList(idList);
            fail("GenreListIsEmptyException should be thrown");
        } catch (GenreListIsEmptyException e) {
        }
        assertThat(allByIdList).isNull();
    }

    @Test
    void getById() {
        Genre genre = mock(Genre.class);
        GenreMapper genreMapper = mock(GenreMapper.class);
        given(mapperDTO.getGenreMapper()).willReturn(genreMapper);
        given(genreRepository.findById(1L)).willReturn(Optional.of(genre));
        given(genreMapper.fromGenre(genre)).willReturn(mock(GenreDTO.class));
        final GenreDTO genreDTO = genreService.getById(1L);
        assertThat(genreDTO).isNotNull();
    }
}
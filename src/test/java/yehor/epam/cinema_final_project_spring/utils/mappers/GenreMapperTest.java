package yehor.epam.cinema_final_project_spring.utils.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GenreMapperTest {
    @Spy
    @InjectMocks
    private GenreMapper genreMapper;

    @Mock
    private MapperDtoFacade facade;

    @Test
    void fromGenre() {
        Genre genre = new Genre(1L, "name");
        final GenreDTO genreDTO = genreMapper.fromGenre(genre);
        Assertions.assertEquals(genre.getId(), genreDTO.getId());
        Assertions.assertEquals(genre.getName(), genreDTO.getName());
    }

    @Test
    void toGenre() {
        GenreDTO genreDto = new GenreDTO(1L, "name");
        final Genre genre = genreMapper.toGenre(genreDto);
        Assertions.assertEquals(genreDto.getId(), genre.getId());
        Assertions.assertEquals(genreDto.getName(), genre.getName());
    }

    @Test
    void fromGenreList() {
        List<Genre> genreList = mock(List.class);
        final List<GenreDTO> genreDTOList = genreMapper.fromGenreList(genreList);
        Assertions.assertEquals(genreList.size(), genreDTOList.size());
    }

    @Test
    void toGenreList() {
        List<GenreDTO> genreDTOS = mock(List.class);
        final List<Genre> genres = genreMapper.toGenreList(genreDTOS);
        Assertions.assertEquals(genreDTOS.size(), genres.size());
    }
}
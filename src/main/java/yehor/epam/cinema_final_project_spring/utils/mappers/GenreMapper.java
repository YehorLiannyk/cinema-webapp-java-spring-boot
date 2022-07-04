package yehor.epam.cinema_final_project_spring.utils.mappers;

import lombok.extern.slf4j.Slf4j;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.ArrayList;
import java.util.List;
/**
 * Mapper for {@link Genre} and {@link GenreDTO}
 */
@Slf4j
public class GenreMapper {

    private final MapperDtoFacade facade;

    public GenreMapper(MapperDtoFacade facade) {
        this.facade = facade;
    }

    public GenreDTO fromGenre(Genre genre) {
        return new GenreDTO(genre.getId(), genre.getName());
    }

    public Genre toGenre(GenreDTO genreDTO) {
        final Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());
        return genre;
    }

    public List<GenreDTO> fromGenreList(List<Genre> genreList) {
        List<GenreDTO> list = new ArrayList<>();
        if (genreList != null)
            genreList.forEach(genre -> list.add(fromGenre(genre)));
        return list;
    }

    public List<Genre> toGenreList(List<GenreDTO> genreDTOList) {
        List<Genre> list = new ArrayList<>();
        if (genreDTOList != null)
            genreDTOList.forEach(genre -> list.add(toGenre(genre)));
        return list;
    }
}

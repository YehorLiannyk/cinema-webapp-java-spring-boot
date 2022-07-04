package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreNotFoundException;

import java.util.List;

/**
 * Genre Service class
 */
public interface GenreService {
    /**
     * Get all genres
     *
     * @return all genre List
     * @throws GenreListIsEmptyException throw when received list is empty
     */
    List<GenreDTO> getAll() throws GenreListIsEmptyException;

    /**
     * Get genre list by its ids
     *
     * @param ids genre ids
     * @return genre list
     */
    List<GenreDTO> getAllByIdList(List<Long> ids);

    /**
     * Get genre by id
     *
     * @param id genre id
     * @return genre
     * @throws GenreNotFoundException throw when genre is not found
     */
    GenreDTO getById(Long id) throws GenreNotFoundException;
}

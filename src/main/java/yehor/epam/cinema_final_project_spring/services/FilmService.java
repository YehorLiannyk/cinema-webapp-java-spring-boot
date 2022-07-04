package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmNotFoundException;

import java.util.List;

/**
 * Film Service class
 */
public interface FilmService {
    /**
     * Get all film paginated
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @return Page of Films
     */
    Page<FilmDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize);

    /**
     * Save film
     *
     * @param filmDTO film
     */
    void save(FilmDTO filmDTO);

    /**
     * Get film by its id
     *
     * @param id film id
     * @return film
     * @throws FilmNotFoundException throw when couldn't find film
     */
    FilmDTO getById(Long id) throws FilmNotFoundException;

    /**
     * Delete film by id
     *
     * @param id film id
     * @throws FilmNotFoundException throw when couldn't find film
     */
    void deleteById(Long id) throws FilmNotFoundException;

    /**
     * Get all films
     *
     * @return film list
     * @throws FilmListIsEmptyException throw when received film list is empty
     */
    List<FilmDTO> getAll() throws FilmListIsEmptyException;

}

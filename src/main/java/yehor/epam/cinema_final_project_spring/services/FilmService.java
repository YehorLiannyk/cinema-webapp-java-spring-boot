package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.film.FilmNotFoundException;

import java.util.List;

public interface FilmService {
    Page<FilmDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize);

    void save(FilmDTO filmDTO);

    FilmDTO getById(Long id) throws FilmNotFoundException;

    void deleteById(Long id) throws FilmNotFoundException;

    List<FilmDTO> getAll() throws FilmListIsEmptyException;

}

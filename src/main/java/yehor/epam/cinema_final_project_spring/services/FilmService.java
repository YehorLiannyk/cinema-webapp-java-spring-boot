package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;

import java.util.List;

public interface FilmService {
    Page<FilmDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize);

    void save(FilmDTO filmDTO);

}

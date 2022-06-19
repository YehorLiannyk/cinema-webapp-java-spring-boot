package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.exceptions.GenreListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.GenreNotExistException;

import java.util.List;

public interface GenreService {
    List<GenreDTO> getAll() throws GenreListIsEmptyException;
    List<GenreDTO> getAllByIdList(List<Long> ids);

    GenreDTO getById(Long id) throws GenreNotExistException;
}

package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.entities.Film;

import java.util.List;

public interface FilmService extends Paginable {
    List<Film> getAllSortedByIdAndPaginated(int pageNo, int pageSize);

}

package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.repositories.FilmRepository;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.GenreService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreService genreService;
    private final MapperDTO mapperDTO;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository, GenreService genreService, MapperDTO mapperDTO) {
        this.filmRepository = filmRepository;
        this.genreService = genreService;
        this.mapperDTO = mapperDTO;
    }

    public Page<FilmDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<Film> filmPage = filmRepository.findAllByOrderByIdDesc(pageable);
        return mapperDTO.fromFilmPage(filmPage);
    }

    @Override
    public void save(FilmDTO filmDTO) {
        final Film film = mapperDTO.toFilm(filmDTO);
        log.debug("Saving film: " + film.toString());
        filmRepository.save(film);
    }

}

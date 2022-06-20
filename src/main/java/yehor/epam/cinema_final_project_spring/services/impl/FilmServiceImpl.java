package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.exceptions.FilmListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.FilmNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.FilmRepository;
import yehor.epam.cinema_final_project_spring.services.FilmService;
import yehor.epam.cinema_final_project_spring.services.GenreService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;
import java.util.Optional;

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

    @Override
    public FilmDTO getById(Long id) throws FilmNotFoundException {
        final Optional<Film> optional = filmRepository.findById(id);
        final Film film = optional.orElseThrow(FilmNotFoundException::new);
        return mapperDTO.fromFilm(film);
    }

    @Override
    public void deleteById(Long id) throws FilmNotFoundException {
        if (!filmRepository.existsById(id)) {
            log.debug("There is no film with id = " + id);
            throw new FilmNotFoundException("Couldn't find film while deleting");
        }
        filmRepository.deleteById(id);
        log.debug("Film with ex-id: " + id + " was successfully deleted");
    }

    @Override
    public List<FilmDTO> getAll() throws FilmListIsEmptyException {
        final List<Film> all = filmRepository.findAll();
        if (all.isEmpty()) {
            log.debug("Couldn't get all films, cause film list is empty");
            throw new FilmListIsEmptyException("Film list is empty");
        }
        return mapperDTO.fromFilmList(all);
    }

}

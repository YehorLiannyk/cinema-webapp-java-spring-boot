package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.genre.GenreNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.GenreRepository;
import yehor.epam.cinema_final_project_spring.services.GenreService;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final MapperDtoFacade mapperDTO;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, MapperDtoFacade mapperDTO) {
        this.genreRepository = genreRepository;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<GenreDTO> getAll() throws GenreListIsEmptyException {
        final List<Genre> all = genreRepository.findAll();
        if (all.isEmpty()) {
            log.error("Received genreList is empty");
            throw new GenreListIsEmptyException();
        }
        return mapperDTO.getGenreMapper().fromGenreList(all);
    }

    @Override
    public List<GenreDTO> getAllByIdList(List<Long> ids) {
        final List<Genre> byIdIn = genreRepository.findByIdIn(ids);
        if (byIdIn.isEmpty()) {
            log.error("Received genreList is empty");
            throw new GenreListIsEmptyException();
        }
        return mapperDTO.getGenreMapper().fromGenreList(byIdIn);
    }

    @Override
    public GenreDTO getById(Long id) throws GenreNotFoundException {
        final Optional<Genre> optional = genreRepository.findById(id);
        final Genre genre = optional.orElseThrow(GenreNotFoundException::new);
        return mapperDTO.getGenreMapper().fromGenre(genre);
    }
}

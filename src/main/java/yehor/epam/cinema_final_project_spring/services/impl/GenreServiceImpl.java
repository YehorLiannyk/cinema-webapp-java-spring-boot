package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;
import yehor.epam.cinema_final_project_spring.entities.Genre;
import yehor.epam.cinema_final_project_spring.exceptions.GenreListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.GenreNotExistException;
import yehor.epam.cinema_final_project_spring.exceptions.UserNotExistException;
import yehor.epam.cinema_final_project_spring.repositories.GenreRepository;
import yehor.epam.cinema_final_project_spring.services.GenreService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final MapperDTO mapperDTO;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, MapperDTO mapperDTO) {
        this.genreRepository = genreRepository;
        this.mapperDTO = mapperDTO;
    }

    public List<GenreDTO> getAll() throws GenreListIsEmptyException {
        final List<Genre> all = genreRepository.findAll();
        final List<GenreDTO> genreDTOList = mapperDTO.fromGenreList(all);
        if (genreDTOList.isEmpty()) {
            log.error("Received genreDTOList is empty");
            throw new GenreListIsEmptyException();
        }
        return genreDTOList;
    }

    @Override
    public List<GenreDTO> getAllByIdList(List<Long> ids) {
        final List<Genre> byIdIn = genreRepository.findByIdIn(ids);
        return mapperDTO.fromGenreList(byIdIn);
    }

    @Override
    public GenreDTO getById(Long id) throws GenreNotExistException {
        final Optional<Genre> optional = genreRepository.findById(id);
        final Genre genre = optional.orElseThrow(GenreNotExistException::new);
        return mapperDTO.fromGenre(genre);
    }
}

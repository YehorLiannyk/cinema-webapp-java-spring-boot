package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.repositories.FilmRepository;
import yehor.epam.cinema_final_project_spring.services.FilmService;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllSortedByIdAndPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return filmRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public Long getTotalAmount() {
        return filmRepository.count();
    }

}

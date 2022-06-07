package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yehor.epam.cinema_final_project_spring.entities.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findAllByOrderByIdDesc(Pageable pageable);
}

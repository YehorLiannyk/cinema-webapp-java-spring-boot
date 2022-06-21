package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yehor.epam.cinema_final_project_spring.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Page<Session> findAllByOrderByIdDesc(Pageable pageable);
}

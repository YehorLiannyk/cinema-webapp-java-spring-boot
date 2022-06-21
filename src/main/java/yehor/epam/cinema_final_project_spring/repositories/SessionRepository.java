package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yehor.epam.cinema_final_project_spring.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Page<Session> findAllByOrderByIdDesc(Pageable pageable);

    Page<Session> findAll(Pageable pageable);

    //@Query(value = "SELECT s FROM Session s WHERE s.seatsAmount > 0")
    Page<Session> findBySeatsAmountGreaterThanEqual(Pageable pageable, Integer seatsAmount);
}

package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.repositories.custom.SessionSeatRepository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>, SessionSeatRepository {
    Page<Session> findAllByOrderByIdDesc(Pageable pageable);

    Page<Session> findAll(Pageable pageable);

    @Query(value = "SELECT s.seatList.size FROM Session as s WHERE s.id=?1")
    Long countFreeSeats(Long sessionId);

    @Query(value = "SELECT s FROM Session AS s WHERE s.seatList.size >= ?1")
    Page<Session> findAllBySeatListSize(Integer minListSize, Pageable pageable);

}

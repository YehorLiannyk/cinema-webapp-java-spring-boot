package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.repositories.custom.CustomSessionRepository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>, CustomSessionRepository {
    Page<Session> findAllByOrderByIdDesc(Pageable pageable);

    Page<Session> findAll(Pageable pageable);

    /**
     * Count free seats by session id
     *
     * @param sessionId session id
     * @return amount of free seats (0 - when no free seats)
     */
    @Query(value = "SELECT s.seatList.size FROM Session as s WHERE s.id=?1")
    Long countFreeSeats(Long sessionId);

    /**
     * Get Session page, where sessions have seatList.size() equal or bigger than {minListSize}
     *
     * @param minListSize minimum session seatList size
     * @param pageable    Pageable
     * @return Page of appropriate Session
     */
    @Query(value = "SELECT s FROM Session AS s WHERE s.seatList.size >= ?1")
    Page<Session> findAllBySeatListSize(Integer minListSize, Pageable pageable);

}

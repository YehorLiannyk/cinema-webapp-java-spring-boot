package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.repositories.custom.CustomSessionRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long>, CustomSessionRepository {
    Page<Session> findAllByOrderByIdDesc(Pageable pageable);

    Page<Session> findAll(Pageable pageable);

    Page<Session> findBySeatsAmountGreaterThanEqual(Pageable pageable, Integer seatsAmount);


    //@Query(value = "SELECT s.seatList FROM Session as s WHERE ?2 = s AND ?1 member of s.seatList")

/*    @Query(value = "SELECT s.seatList FROM Session as s WHERE ?2 = s AND ?1 member of s.seatList")
        //Boolean isSeatFreeForSession(Long seatId, Long sessionId);
    List<Seat> isSeatFreeForSession(Seat seat, Session session);*/
}

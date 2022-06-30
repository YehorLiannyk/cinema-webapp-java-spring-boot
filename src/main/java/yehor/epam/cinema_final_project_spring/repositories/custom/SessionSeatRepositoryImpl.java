package yehor.epam.cinema_final_project_spring.repositories.custom;

import org.springframework.transaction.annotation.Transactional;
import yehor.epam.cinema_final_project_spring.entities.Seat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class SessionSeatRepositoryImpl implements SessionSeatRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isSeatFreeBySession(Long seatId, Long sessionId) {
        final Query query = entityManager.createNativeQuery("select * from session_seats where seat_id=? and session_id=?");
        query.setParameter(1, seatId);
        query.setParameter(2, sessionId);
        final List resultList = query.getResultList();
        return resultList != null && !resultList.isEmpty();
    }

    @Override
    public boolean isSeatListFreeBySession(List<Seat> seatList, Long sessionId) {
        return seatList.stream()
                .allMatch(seat -> isSeatFreeBySession(seat.getId(), sessionId));
    }

    @Transactional
    @Override
    public void deleteSessionSeat(Long seatId, Long sessionId) {
        final Query query = entityManager.createNativeQuery("delete from session_seats where seat_id=? and session_id=?");
        query.setParameter(1, seatId);
        query.setParameter(2, sessionId);
        query.executeUpdate();
    }

    @Transactional
    @Override
    public void deleteSessionSeatList(List<Seat> seatList, Long sessionId) {
        seatList.forEach(seat -> deleteSessionSeat(seat.getId(), sessionId));
    }
}

package yehor.epam.cinema_final_project_spring.repositories.custom;

import yehor.epam.cinema_final_project_spring.entities.Seat;

import java.util.List;

public interface SessionSeatRepository {
    boolean isSeatFreeBySession(Long seatId, Long sessionId);

    boolean isSeatListFreeBySession(List<Seat> seatList, Long sessionId);

    void deleteSessionSeat(Long seatId, Long sessionId);

    void deleteSessionSeatList(List<Seat> seatList, Long sessionId);
}

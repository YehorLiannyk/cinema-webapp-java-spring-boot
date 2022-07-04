package yehor.epam.cinema_final_project_spring.repositories.custom;

import yehor.epam.cinema_final_project_spring.entities.Seat;

import java.util.List;

/**
 * Custom repository for session's seat list
 */
public interface CustomSessionRepository {
    /**
     * Checking is the seat free for the session
     *
     * @param seatId    seat id
     * @param sessionId session id
     * @return true if seat is free, false - is reserved
     */
    boolean isSeatFreeBySession(Long seatId, Long sessionId);

    /**
     * Checking is the all seat free for the session
     *
     * @param seatList  seat list
     * @param sessionId session id
     * @return true if seats are free, false - any or all are reserved
     */
    boolean isSeatListFreeBySession(List<Seat> seatList, Long sessionId);

    /**
     * Remove seat from session seat list, called when user bought ticket
     *
     * @param seatId    seat id
     * @param sessionId session id
     */
    void deleteSessionSeat(Long seatId, Long sessionId);

    /**
     * Remove seat list from session seat list, called when user bought ticket
     *
     * @param seatList  seat list
     * @param sessionId session id
     */
    void deleteSessionSeatList(List<Seat> seatList, Long sessionId);
}

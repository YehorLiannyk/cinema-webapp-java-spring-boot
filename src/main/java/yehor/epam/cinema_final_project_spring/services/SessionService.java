package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.entities.Session;

import java.util.List;
import java.util.Map;

/**
 * Session Service class
 */
public interface SessionService {
    /**
     * Save session
     *
     * @param sessionDTO session
     */
    void save(SessionDTO sessionDTO);

    /**
     * Get all session page sorted by default(by id)
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @return page of Sessions
     */
    Page<SessionDTO> getAll(int pageNo, int pageSize);

    /**
     * Get all session page
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @param filter   filter param
     * @param sort     sorter param
     * @param method   method param (asc or desc)
     */
    Page<SessionDTO> getAllSortedAndFiltered(int pageNo, int pageSize, String filter, String sort, String method);

    /**
     * Delete session by id
     *
     * @param id session id
     */
    void deleteById(Long id);

    /**
     * Get all sessions
     *
     * @return session list
     */
    List<SessionDTO> getAll();

    /**
     * Get session by id
     *
     * @param id session id
     * @return session
     */
    SessionDTO getById(Long id);

    /**
     * Get session entity by id
     *
     * @param id session id
     * @return session entity
     */
    Session getEntityById(Long id);

    /**
     * Get map of free and reserved session seat list, where Boolean is seat free, otherwise - false
     *
     * @param id session id
     * @return map of seat and its free status
     */
    Map<SeatDTO, Boolean> getFreeAndReservedSeatMap(Long id);

    /**
     * Check if seat is free by session
     *
     * @param seatId    seat id
     * @param sessionId session id
     * @return true is free, otherwise - false
     */
    boolean isSeatFreeBySession(Long seatId, Long sessionId);

    /**
     * Checking is the all seat free for the session
     *
     * @param seatDTOList seat list
     * @param sessionId   session id
     * @return true if seats are free, false - any or all are reserved
     */
    boolean isSeatListFreeBySession(List<SeatDTO> seatDTOList, Long sessionId);

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
     * @param seatDTOList seat list
     * @param sessionId   session id
     */
    void deleteSessionSeatList(List<SeatDTO> seatDTOList, Long sessionId);

    /**
     * Count free seats by session
     *
     * @param sessionId session id
     * @return amount of free seats
     */
    Long countFreeSeats(Long sessionId);
}

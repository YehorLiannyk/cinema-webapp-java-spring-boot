package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;

import java.util.List;
import java.util.Map;

public interface SessionService {
    void save(SessionDTO sessionDTO);

    Page<SessionDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize);

    Page<SessionDTO> getAll(int pageNo, int pageSize, String filter, String sort, String method);

    void deleteById(Long id);

    //delete
    List<SessionDTO> getAll();

    SessionDTO getById(Long id);

    Session getEntityById(Long id);

    Map<SeatDTO, Boolean> getFreeAndReservedSeatMap(Long id);

    boolean isSeatFreeBySession(Long seatId, Long sessionId);

    boolean isSeatListFreeBySession(List<SeatDTO> seatDTOList, Long sessionId);

    void deleteSessionSeat(Long seatId, Long sessionId);

    void deleteSessionSeatList(List<SeatDTO> seatDTOList, Long sessionId);

}

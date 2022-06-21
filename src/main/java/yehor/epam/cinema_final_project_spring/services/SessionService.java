package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.entities.Session;

import java.util.List;
import java.util.Map;

public interface SessionService {
    void save(SessionDTO sessionDTO);

    Page<SessionDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize);

    void deleteById(Long id);

    List<SessionDTO> getAll();

    SessionDTO getById(Long id);

    Map<SeatDTO, Boolean> getFreeAndReservedSeatMap(Long id);
}

package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllByUserId(long userId);

    Page<TicketDTO> getAllByUserIdPage(int page, int size, long userId);

    List<TicketDTO> formTicketList(List<SeatDTO> seatDTOList, Long sessionId, Long userId);

    BigDecimal countTotalCost(List<TicketDTO> ticketDTOList);

    void save(List<TicketDTO> ticketDTOList);

    TicketDTO getById(Long id);
}

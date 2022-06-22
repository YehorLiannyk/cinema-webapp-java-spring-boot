package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.entities.Ticket;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllByUserId(long userId);

    List<TicketDTO> formTicketList(List<SeatDTO> seatDTOList, Long sessionId, Long userId);

    BigDecimal countTotalCost(List<TicketDTO> ticketDTOList);

    void save(List<TicketDTO> ticketDTOList);

    TicketDTO getById(Long id);
}

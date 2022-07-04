package yehor.epam.cinema_final_project_spring.services;

import org.springframework.data.domain.Page;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Ticket Service class
 */
public interface TicketService {
    /**
     * Get all tickets by user id
     *
     * @param userId user id
     * @return ticket list
     */
    List<TicketDTO> getAllByUserId(long userId);

    /**
     * Get ticket page by user id
     *
     * @param page   page number
     * @param size   page size
     * @param userId user id
     * @return ticket page
     */
    Page<TicketDTO> getAllByUserIdPage(int page, int size, long userId);

    /**
     * Form ticket list from received parameters, but not save it
     *
     * @param seatDTOList sead list
     * @param sessionId   session id
     * @param userId      user id
     * @return list of formed ticket
     */
    List<TicketDTO> formTicketList(List<SeatDTO> seatDTOList, Long sessionId, Long userId);

    /**
     * Count total cost of tickets from list
     *
     * @param ticketDTOList ticket list
     * @return total cost
     */
    BigDecimal countTotalCost(List<TicketDTO> ticketDTOList);

    /**
     * Save ticket
     *
     * @param ticketDTOList ticket list
     */
    void save(List<TicketDTO> ticketDTOList);

    /**
     * Get ticket by id
     *
     * @param id ticket id
     * @return ticket
     */
    TicketDTO getById(Long id);
}

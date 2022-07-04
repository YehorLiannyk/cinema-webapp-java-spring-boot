package yehor.epam.cinema_final_project_spring.utils.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import yehor.epam.cinema_final_project_spring.dto.*;
import yehor.epam.cinema_final_project_spring.entities.*;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.ArrayList;
import java.util.List;
/**
 * Mapper for {@link Ticket} and {@link TicketDTO}
 */
@Slf4j
public class TicketMapper {
    private final MapperDtoFacade facade;

    public TicketMapper(MapperDtoFacade facade) {
        this.facade = facade;
    }


    public Page<TicketDTO> fromTicketPage(Page<Ticket> ticketPage) {
        final List<TicketDTO> ticketDTOList = fromTicketList(ticketPage.getContent());
        return new PageImpl<>(ticketDTOList, ticketPage.getPageable(), ticketPage.getTotalElements());
    }


    public TicketDTO fromTicket(Ticket ticket) {
        final SessionDTO session = facade.getSessionMapper().fromSession(ticket.getSession());
        final UserDTO user = facade.getUserMapper().fromUser(ticket.getUser());
        final SeatDTO seat = facade.getSeatMapper().fromSeat(ticket.getSeat());
        return new TicketDTO(
                ticket.getId(),
                session,
                user,
                seat
        );
    }


    public Ticket toTicket(TicketDTO ticketDTO) {
        final Session session = facade.getSessionMapper().toSession(ticketDTO.getSession());
        final User user = facade.getUserMapper().toUser(ticketDTO.getUser());
        final Seat seat = facade.getSeatMapper().toSeat(ticketDTO.getSeat());
        Ticket ticket = new Ticket();
        ticket.setId(ticketDTO.getId());
        ticket.setSession(session);
        ticket.setUser(user);
        ticket.setSeat(seat);
        return ticket;
    }

    public List<TicketDTO> fromTicketList(List<Ticket> ticketList) {
        List<TicketDTO> list = new ArrayList<>();
        if (ticketList != null)
            ticketList.forEach(ticket -> list.add(fromTicket(ticket)));
        return list;
    }

    public List<Ticket> toTicketList(List<TicketDTO> ticketDTOList) {
        List<Ticket> list = new ArrayList<>();
        if (ticketDTOList != null)
            ticketDTOList.forEach(ticketDTO -> list.add(toTicket(ticketDTO)));
        return list;
    }
}

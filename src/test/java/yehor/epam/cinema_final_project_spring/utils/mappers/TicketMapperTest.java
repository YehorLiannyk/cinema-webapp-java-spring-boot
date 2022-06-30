package yehor.epam.cinema_final_project_spring.utils.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yehor.epam.cinema_final_project_spring.dto.*;
import yehor.epam.cinema_final_project_spring.entities.*;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketMapperTest {
    @Spy
    @InjectMocks
    private TicketMapper ticketMapper;

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private MapperDtoFacade facade;

    @Mock
    private SeatMapper seatMapper;

    @Mock
    private UserMapper userMapper;

    @Test
    void fromTicketPage() {
        Page<Ticket> tickets = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        given(tickets.getPageable()).willReturn(pageable);
        final Page<TicketDTO> ticketPage = ticketMapper.fromTicketPage(tickets);
        Assertions.assertEquals(tickets.getTotalElements(), ticketPage.getTotalElements());
    }

    @Test
    void fromTicket() {
        Ticket ticket = new Ticket();
        Session session = mock(Session.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);
        Seat seat = mock(Seat.class);
        SeatDTO seatDTO = mock(SeatDTO.class);
        User user = mock(User.class);
        UserDTO userDTO = mock(UserDTO.class);

        ticket.setSeat(seat);
        ticket.setUser(user);
        ticket.setSession(session);
        ticket.setId(1L);

        given(facade.getSeatMapper()).willReturn(seatMapper);
        given(seatMapper.fromSeat(seat)).willReturn(seatDTO);
        given(facade.getSessionMapper()).willReturn(sessionMapper);
        given(sessionMapper.fromSession(session)).willReturn(sessionDTO);
        given(facade.getUserMapper()).willReturn(userMapper);
        given(userMapper.fromUser(user)).willReturn(userDTO);

        final TicketDTO ticketDTO = ticketMapper.fromTicket(ticket);

        Assertions.assertEquals(ticket.getId(), ticketDTO.getId());
    }

    @Test
    void toTicket() {
        TicketDTO dto = new TicketDTO();
        SessionDTO sessionDto = mock(SessionDTO.class);
        Session session = mock(Session.class);
        SeatDTO seatDto = mock(SeatDTO.class);
        Seat seat = mock(Seat.class);
        UserDTO userDto = mock(UserDTO.class);
        User user = mock(User.class);

        dto.setSeat(seatDto);
        dto.setUser(userDto);
        dto.setSession(sessionDto);
        dto.setId(1L);

        given(facade.getSeatMapper()).willReturn(seatMapper);
        given(seatMapper.toSeat(seatDto)).willReturn(seat);
        given(facade.getSessionMapper()).willReturn(sessionMapper);
        given(sessionMapper.toSession(sessionDto)).willReturn(session);
        given(facade.getUserMapper()).willReturn(userMapper);
        given(userMapper.toUser(userDto)).willReturn(user);

        final Ticket ticket = ticketMapper.toTicket(dto);

        Assertions.assertEquals(dto.getId(), ticket.getId());
    }

    @Test
    void fromTicketList() {
        List<Ticket> ticketList = mock(List.class);
        final List<TicketDTO> dtoList = ticketMapper.fromTicketList(ticketList);
        Assertions.assertEquals(ticketList.size(), dtoList.size());
    }

    @Test
    void toTicketList() {
        List<TicketDTO> ticketDTOS = mock(List.class);
        final List<Ticket> ticketList = ticketMapper.toTicketList(ticketDTOS);
        Assertions.assertEquals(ticketDTOS.size(), ticketList.size());
    }
}
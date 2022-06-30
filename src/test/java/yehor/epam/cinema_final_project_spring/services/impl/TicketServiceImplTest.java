package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.entities.Ticket;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatIsAlreadyReservedException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketListIsEmptyException;
import yehor.epam.cinema_final_project_spring.repositories.TicketRepository;
import yehor.epam.cinema_final_project_spring.services.SessionService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;
import yehor.epam.cinema_final_project_spring.utils.mappers.SeatMapper;
import yehor.epam.cinema_final_project_spring.utils.mappers.SessionMapper;
import yehor.epam.cinema_final_project_spring.utils.mappers.TicketMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {
    @Spy
    @InjectMocks
    TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SessionService sessionService;

    @Mock
    private UserService userService;

    @Mock
    private MapperDtoFacade mapperDTO;

    @Test
    void getAllByUserId() {
        TicketMapper ticketMapper = mock(TicketMapper.class);
        given(mapperDTO.getTicketMapper()).willReturn(ticketMapper);
        given(ticketRepository.findAllByUserId(1L)).willReturn(List.of());
        given(ticketService.getAllByUserId(1L)).willReturn(List.of());
        final List<TicketDTO> dtoList = ticketService.getAllByUserId(1L);
        assertThat(dtoList).isNotNull();
    }

    @Test
    void getAllByUserIdPage() {
        final Pageable pageable = PageRequest.of(1, 1);
        given(ticketRepository.findAllByUserId(1L, pageable)).willReturn(Page.empty());
        final int pageNumber = pageable.getPageNumber();
        final int pageSize = pageable.getPageSize();
        TicketMapper ticketMapper = mock(TicketMapper.class);
        given(mapperDTO.getTicketMapper()).willReturn(ticketMapper);
        given(ticketService.getAllByUserIdPage(pageNumber, pageSize, 1L)).willReturn(Page.empty());
        final Page<TicketDTO> ticketDTOPage = ticketService.getAllByUserIdPage(pageNumber, pageSize, 1L);
        assertThat(ticketDTOPage).isNotNull();
    }

    @Test
    void formTicketList() {
        List<SeatDTO> seatDTOList = mock(List.class);
        List<Seat> seatList = mock(List.class);
        Session session = mock(Session.class);
        User user = mock(User.class);
        TicketMapper ticketMapper = mock(TicketMapper.class);
        SeatMapper seatMapper = mock(SeatMapper.class);
        given(mapperDTO.getTicketMapper()).willReturn(ticketMapper);
        given(mapperDTO.getSeatMapper()).willReturn(seatMapper);
        given(seatMapper.toSeatList(seatDTOList)).willReturn(seatList);
        given(sessionService.getEntityById(1L)).willReturn(session);
        given(userService.getEntityById(1L)).willReturn(user);
        assertThat(ticketService.formTicketList(seatDTOList, 1L, 1L)).isNotNull();
    }


    @Test
    void countTotalCostEmptyList() {
        List<TicketDTO> ticketDTOList = List.of();
        Assertions.assertEquals(BigDecimal.ZERO, ticketService.countTotalCost(ticketDTOList));
    }

    @Test
    void countTotalCostTwoElementList() {
        final TicketDTO ticketDTO = mock(TicketDTO.class);
        final SessionDTO sessionDTO = mock(SessionDTO.class);
        given(ticketDTO.getSession()).willReturn(sessionDTO);
        given(ticketDTO.getSession().getTicketPrice()).willReturn(BigDecimal.valueOf(100));
        List<TicketDTO> ticketDTOList = List.of(ticketDTO, ticketDTO);
        Assertions.assertEquals(BigDecimal.valueOf(200), ticketService.countTotalCost(ticketDTOList));
    }

    @Test
    void save() {
        TicketDTO ticketDTO = mock(TicketDTO.class);
        List<TicketDTO> list = List.of(ticketDTO);
        SeatDTO seatDTO = mock(SeatDTO.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);
        TicketMapper ticketMapper = mock(TicketMapper.class);

        given(mapperDTO.getTicketMapper()).willReturn(ticketMapper);
        given(ticketDTO.getSeat()).willReturn(seatDTO);
        given(ticketDTO.getSession()).willReturn(sessionDTO);
        given(sessionService.isSeatFreeBySession(seatDTO.getId(), sessionDTO.getId())).willReturn(true);
        ticketService.save(list);
        then(ticketService).should(times(1)).save(list);
    }

    @Test
    void saveThrowTicketListIsEmptyException() {
        Ticket ticket = mock(Ticket.class);
        List<TicketDTO> list = List.of();
        List<Ticket> ticketList = List.of(ticket);

        try {
            ticketService.save(list);
            fail("TicketListIsEmptyException should be thrown");
        } catch (TicketListIsEmptyException e) {
        }
        then(ticketRepository).should(never()).saveAll(ticketList);
    }

    @Test
    void saveThrowSeatIsAlreadyReservedException() {
        Ticket ticket = mock(Ticket.class);
        TicketDTO ticketDTO = mock(TicketDTO.class);
        List<TicketDTO> list = List.of(ticketDTO);
        SeatDTO seatDTO = mock(SeatDTO.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);

        given(ticketDTO.getSeat()).willReturn(seatDTO);
        given(ticketDTO.getSession()).willReturn(sessionDTO);
        given(sessionService.isSeatFreeBySession(seatDTO.getId(), sessionDTO.getId())).willReturn(false);
        try {
            ticketService.save(list);
            fail("SeatIsAlreadyReservedException should be thrown");
        } catch (SeatIsAlreadyReservedException e) {
        }
        then(ticketService).should(times(1)).save(list);
    }

    @Test
    void getById() {
        Ticket ticket = mock(Ticket.class);
        given(ticketRepository.findById(1L)).willReturn(Optional.of(ticket));
        TicketMapper ticketMapper = mock(TicketMapper.class);
        given(mapperDTO.getTicketMapper()).willReturn(ticketMapper);
        given(ticketMapper.fromTicket(ticket)).willReturn(mock(TicketDTO.class));
        final TicketDTO ticketDTO = ticketService.getById(1L);
        assertThat(ticketDTO).isNotNull();
    }
}
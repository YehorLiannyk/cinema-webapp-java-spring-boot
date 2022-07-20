package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.entities.Ticket;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatIsAlreadyReservedException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.TicketRepository;
import yehor.epam.cinema_final_project_spring.services.SessionService;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final SessionService sessionService;
    private final UserService userService;
    private final MapperDtoFacade mapperDTO;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, SessionService sessionService, UserService userService,
                             MapperDtoFacade mapperDTO) {
        this.ticketRepository = ticketRepository;
        this.sessionService = sessionService;
        this.userService = userService;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<TicketDTO> getAllByUserId(long userId) {
        final List<Ticket> allByUserId = ticketRepository.findAllByUserId(userId);
        return mapperDTO.getTicketMapper().fromTicketList(allByUserId);
    }

    @Override
    public Page<TicketDTO> getAllByUserIdPage(int page, int size, long userId) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Ticket> allByUserId = ticketRepository.findAllByUserId(userId, pageable);
        return mapperDTO.getTicketMapper().fromTicketPage(allByUserId);
    }

    @Override
    public List<TicketDTO> formTicketList(List<SeatDTO> seatDTOList, Long sessionId, Long userId) {
        List<Ticket> ticketList = new ArrayList<>();
        final List<Seat> seatList = mapperDTO.getSeatMapper().toSeatList(seatDTOList);
        final Session session = sessionService.getEntityById(sessionId);
        final User user = userService.getEntityById(userId);
        seatList.forEach(seat -> {
            Ticket ticket = new Ticket();
            ticket.setSeat(seat);
            ticket.setSession(session);
            ticket.setUser(user);
            ticketList.add(ticket);
        });
        return mapperDTO.getTicketMapper().fromTicketList(ticketList);
    }

    @Override
    public BigDecimal countTotalCost(List<TicketDTO> ticketDTOList) {
        BigDecimal totalCost = new BigDecimal(0);
        return ticketDTOList.stream()
                .map(ticketDTO -> ticketDTO.getSession().getTicketPrice())
                .reduce(totalCost, BigDecimal::add);
    }

    @Override
    public void save(List<TicketDTO> ticketDTOList) {
        if (ticketDTOList == null || ticketDTOList.isEmpty()) {
            log.debug("Received ticket list is empty");
            throw new TicketListIsEmptyException();
        }
        final boolean isFree = checkSeatsOfTicketListAreFree(ticketDTOList);
        if (!isFree) {
            log.debug("Couldn't save tickets, seat is already reserved");
            throw new SeatIsAlreadyReservedException();
        }
        final List<Ticket> ticketList = mapperDTO.getTicketMapper().toTicketList(ticketDTOList);
        ticketRepository.saveAll(ticketList);
        deleteSeatsOfTicketList(ticketDTOList);
        log.info("Save tickets to DB and delete session seats, ticketList: " + ticketDTOList);
    }

    /**
     * Checks all seats from Ticket list by its free
     *
     * @param ticketDTOList ticket List
     * @return true - all seats are free, false - all or some seats are reserved already
     */
    private boolean checkSeatsOfTicketListAreFree(List<TicketDTO> ticketDTOList) {
        return ticketDTOList.stream()
                .allMatch(ticketDTO -> {
                    final SeatDTO seat = ticketDTO.getSeat();
                    final SessionDTO session = ticketDTO.getSession();
                    return sessionService.isSeatFreeBySession(seat.getId(), session.getId());
                });
    }

    /**
     * Delete seats from Session free Seat list after buying by Ticket list
     *
     * @param ticketDTOList ticket list
     */
    private void deleteSeatsOfTicketList(List<TicketDTO> ticketDTOList) {
        ticketDTOList
                .forEach(ticketDTO -> {
                    final SeatDTO seat = ticketDTO.getSeat();
                    final SessionDTO session = ticketDTO.getSession();
                    sessionService.deleteSessionSeat(seat.getId(), session.getId());
                    log.debug("Delete session seat, seat: " + seat + ", session: " + session + ", ticketID: " + ticketDTO.getId());
                });
    }

    @Override
    public TicketDTO getById(Long id) {
        final Ticket ticket = ticketRepository.findById(id).orElseThrow(TicketNotFoundException::new);
        return mapperDTO.getTicketMapper().fromTicket(ticket);
    }

}

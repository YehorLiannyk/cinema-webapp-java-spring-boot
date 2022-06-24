package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.entities.Ticket;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.CantSendTicketEmailException;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatIsAlreadyReservedException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.ticket.TicketNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.TicketRepository;
import yehor.epam.cinema_final_project_spring.services.SessionService;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final SessionService sessionService;
    private final UserService userService;
    private final MapperDTO mapperDTO;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, SessionService sessionService, UserService userService,
                             MapperDTO mapperDTO) {
        this.ticketRepository = ticketRepository;
        this.sessionService = sessionService;
        this.userService = userService;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<TicketDTO> getAllByUserId(long userId) {
        final List<Ticket> allByUserId = ticketRepository.findAllByUserId(userId);
        return mapperDTO.fromTicketList(allByUserId);
    }

    @Override
    public List<TicketDTO> formTicketList(List<SeatDTO> seatDTOList, Long sessionId, Long userId) {
        List<Ticket> ticketList = new ArrayList<>();
        final List<Seat> seatList = mapperDTO.toSeatList(seatDTOList);
        final Session session = sessionService.getEntityById(sessionId);
        final User user = userService.getEntityById(userId);
        seatList.forEach(seat -> {
            Ticket ticket = new Ticket();
            ticket.setSeat(seat);
            ticket.setSession(session);
            ticket.setUser(user);
            ticketList.add(ticket);
        });
        return mapperDTO.fromTicketList(ticketList);
    }

    @Override
    public BigDecimal countTotalCost(List<TicketDTO> ticketDTOList) {
        BigDecimal totalCost = new BigDecimal(0);
        for (TicketDTO ticketDTO : ticketDTOList) {
            final BigDecimal ticketPrice = ticketDTO.getSession().getTicketPrice();
            totalCost = totalCost.add(ticketPrice);
        }
        return totalCost;
    }

    @Override
    public void save(List<TicketDTO> ticketDTOList) {
        if (ticketDTOList == null || ticketDTOList.isEmpty()) {
            log.debug("Received ticket list is empty");
            throw new TicketListIsEmptyException();
        }
        log.debug("Received ticketList size: " + ticketDTOList.size() + ", ticketLIst: " + ticketDTOList);
        final boolean isFree = checkSeatsOfTicketListAreFree(ticketDTOList);
        if (!isFree) {
            log.debug("Couldn't save tickets, seat is already reserved");
            throw new SeatIsAlreadyReservedException();
        }
        log.debug("TicketList after check isFree size: " + ticketDTOList.size() + ", ticketLIst: " + ticketDTOList);
        final List<Ticket> ticketList = mapperDTO.toTicketList(ticketDTOList);
        log.debug("TicketList after mapping isFree size: " + ticketDTOList.size() + ", ticketLIst: " + ticketDTOList);
        ticketRepository.saveAll(ticketList);
        deleteSeatsOfTicketList(ticketDTOList);
        log.info("Save tickets to DB and delete session seats");
    }

    private boolean checkSeatsOfTicketListAreFree(List<TicketDTO> ticketDTOList) {
        return ticketDTOList.stream()
                .allMatch(ticketDTO -> {
                    final SeatDTO seat = ticketDTO.getSeat();
                    final SessionDTO session = ticketDTO.getSession();
                    return sessionService.isSeatFreeBySession(seat.getId(), session.getId());
                });
    }

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
        return mapperDTO.fromTicket(ticket);
    }



}

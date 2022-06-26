package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.exceptions.session.SessionListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.session.SessionNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.SessionRepository;
import yehor.epam.cinema_final_project_spring.services.SeatService;
import yehor.epam.cinema_final_project_spring.services.SessionService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.*;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SeatService seatService;
    private final MapperDTO mapperDTO;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, SeatService seatService, MapperDTO mapperDTO) {
        this.sessionRepository = sessionRepository;
        this.seatService = seatService;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public void save(SessionDTO sessionDTO) {
        if (sessionDTO.getSeatList() == null || sessionDTO.getSeatList().isEmpty()) {
            log.debug("Received session has no seats, set default seatList");
            final List<Seat> allEntities = seatService.getAllEntities();
            final List<SeatDTO> seatDTOList = mapperDTO.fromSeatList(allEntities);
            sessionDTO.setSeatList(seatDTOList);
        }
        final Session session = mapperDTO.toSession(sessionDTO);
        sessionRepository.save(session);
        log.debug("Saved session: " + session);
    }

    @Override
    public Page<SessionDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<Session> sessionPage = sessionRepository.findAllByOrderByIdDesc(pageable);
        return mapperDTO.fromSessionPage(sessionPage);
    }

    @Override
    public Page<SessionDTO> getAll(int pageNo, int pageSize, String filter, String sort, String method) {
        log.debug("Sort by: " + sort);
        List<Sort.Order> generalOrders = getSortOrder(sort);
        Sort sorter = Sort.by(generalOrders);
        sorter = getSortWithOrderMethod(sorter, method);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sorter);
        Page<Session> sessionPage = getOrderPageWithFilter(pageable, filter);
        return mapperDTO.fromSessionPage(sessionPage);
    }

    private Page<Session> getOrderPageWithFilter(Pageable pageable, String filter) {
        if (filter.equals(FILTER_SHOW_ONLY_AVAILABLE)) {
            return sessionRepository.findAllBySeatListSize(1, pageable);
        }
        return sessionRepository.findAllBySeatListSize(0, pageable);
    }

    private Sort getSortWithOrderMethod(Sort sort, String method) {
        if (method.equals(SORT_METHOD_DESC)) {
            return sort.descending();
        }
        return sort.ascending();
    }

    private List<Sort.Order> getSortOrder(String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        switch (sort) {
            case SORT_BY_FILM_NAME -> {
                Sort.Order name = new Sort.Order(Sort.DEFAULT_DIRECTION, "film.name");
                orders.add(name);
            }
            case SORT_BY_SEATS_REMAIN -> {
                Sort.Order seats = new Sort.Order(Sort.DEFAULT_DIRECTION, "seatList.size");
                orders.add(seats);
            }
            default -> {
                Sort.Order date = new Sort.Order(Sort.DEFAULT_DIRECTION, "date");
                orders.add(date);
                Sort.Order time = new Sort.Order(Sort.DEFAULT_DIRECTION, "time");
                orders.add(time);
            }
        }
        return orders;
    }

    @Override
    public void deleteById(Long id) {
        checkSessionExisting(id);
        sessionRepository.deleteById(id);
        log.debug("Session with ex-id: " + id + " was successfully deleted");
    }

    private void checkSessionExisting(Long id) throws SessionNotFoundException {
        if (!sessionRepository.existsById(id)) {
            log.debug("There is no session with id = " + id);
            throw new SessionNotFoundException("Couldn't find session while deleting");
        }
    }

    @Override
    public List<SessionDTO> getAll() {
        final List<Session> all = sessionRepository.findAll();
        if (all.isEmpty()) {
            log.debug("Couldn't get all sessions, cause session list is empty");
            throw new SessionListIsEmptyException("Session list is empty");
        }
        return mapperDTO.fromSessionList(all);
    }

    @Override
    public SessionDTO getById(Long id) {
        final Optional<Session> optional = sessionRepository.findById(id);
        final Session session = optional.orElseThrow(SessionNotFoundException::new);
        return mapperDTO.fromSession(session);
    }

    @Override
    public Session getEntityById(Long id) {
        final Optional<Session> optional = sessionRepository.findById(id);
        return optional.orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public Map<SeatDTO, Boolean> getFreeAndReservedSeatMap(Long id) {
        final Optional<Session> optional = sessionRepository.findById(id);
        final Session session = optional.orElseThrow(SessionNotFoundException::new);
        final List<Seat> freeSeatList = session.getSeatList();
        final List<Seat> allSeatList = seatService.getAllEntities();
        Map<SeatDTO, Boolean> seatMap = new LinkedHashMap<>();
        allSeatList.forEach(allSeat -> {
            boolean isEqual = freeSeatList.stream().anyMatch(freeSeat -> freeSeat.equals(allSeat));
            final SeatDTO seatDTO = mapperDTO.fromSeat(allSeat);
            seatMap.put(seatDTO, isEqual);
        });
        return seatMap;
    }

    @Override
    public boolean isSeatFreeBySession(Long seatId, Long sessionId) {
        return sessionRepository.isSeatFreeBySession(seatId, sessionId);
    }

    @Override
    public boolean isSeatListFreeBySession(List<SeatDTO> seatDTOList, Long sessionId) {
        final List<Seat> seatList = mapperDTO.toSeatList(seatDTOList);
        return sessionRepository.isSeatListFreeBySession(seatList, sessionId);
    }

    @Override
    public void deleteSessionSeat(Long seatId, Long sessionId) {
        sessionRepository.deleteSessionSeat(seatId, sessionId);
    }

    @Override
    public void deleteSessionSeatList(List<SeatDTO> seatDTOList, Long sessionId) {
        final List<Seat> seatList = mapperDTO.toSeatList(seatDTOList);
        sessionRepository.deleteSessionSeatList(seatList, sessionId);
    }

    @Override
    public Long countFreeSeats(Long sessionId) {
        return sessionRepository.countFreeSeats(sessionId);
    }
}

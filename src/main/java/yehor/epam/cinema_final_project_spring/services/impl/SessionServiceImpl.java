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

@Slf4j
@Service
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
            final List<Seat> allEntities = seatService.getAllEntities();
            final List<SeatDTO> seatDTOList = mapperDTO.fromSeatList(allEntities);
            sessionDTO.setSeatList(seatDTOList);
        }
        log.debug("SessionDTO: " + sessionDTO);
        final Session session = mapperDTO.toSession(sessionDTO);
        log.debug("Session ticket price = " + session.getTicketPrice());
        sessionRepository.save(session);
    }

    @Override
    public Page<SessionDTO> getAllSortedByIdAndPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<Session> sessionPage = sessionRepository.findAllByOrderByIdDesc(pageable);
        return mapperDTO.fromSessionPage(sessionPage);
    }

    @Override
    public Page<SessionDTO> getAll(int pageNo, int pageSize, String filter, String sort, String method) {
        List<Sort.Order> generalOrders = getSortOrder(sort);
        Sort sorter = Sort.by(generalOrders);
        sorter = getWithOrderMethod(sorter, method);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sorter);
        final Page<Session> all = getPageWithFilter(pageable, filter);
        return mapperDTO.fromSessionPage(all);
    }

    private Page<Session> getPageWithFilter(Pageable pageable, String filter) {
        if (filter.equals(FILTER_SHOW_ONLY_AVAILABLE)) {
            return sessionRepository.findBySeatsAmountGreaterThanEqual(pageable, 1);
        }
        return sessionRepository.findBySeatsAmountGreaterThanEqual(pageable, 0);
    }

    private Sort getWithOrderMethod(Sort sort, String method) {
        Sort sorter = null;
        if (method.equals(SORT_METHOD_DESC)) {
            sorter = sort.descending();
        } else {
            sorter = sort.ascending();
        }
        return sorter;
    }

    private List<Sort.Order> getSortOrder(String sort) {
        log.debug("Sort by: " + sort);
        List<Sort.Order> orders = new ArrayList<>();
        if (sort.equals(SORT_BY_SEATS_REMAIN)) {
            Sort.Order seats = new Sort.Order(Sort.DEFAULT_DIRECTION, "COUNT(seatList)");
            orders.add(seats);
        } else if (sort.equals(SORT_BY_FILM_NAME)) {
            Sort.Order name = new Sort.Order(Sort.DEFAULT_DIRECTION, "film.name");
            orders.add(name);
        } else {
            Sort.Order date = new Sort.Order(Sort.DEFAULT_DIRECTION, "date");
            orders.add(date);
            Sort.Order time = new Sort.Order(Sort.DEFAULT_DIRECTION, "time");
            orders.add(time);
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
}

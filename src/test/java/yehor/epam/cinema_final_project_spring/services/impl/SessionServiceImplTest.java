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
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.exceptions.session.SessionListIsEmptyException;
import yehor.epam.cinema_final_project_spring.exceptions.session.SessionNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.SessionRepository;
import yehor.epam.cinema_final_project_spring.services.SeatService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.*;
import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @Spy
    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SeatService seatService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private MapperDTO mapperDTO;

    @Test
    void saveNotEmptySeatList() {
        Session session = mock(Session.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);
        given(sessionRepository.save(session)).willReturn(session);
        given(mapperDTO.toSession(sessionDTO)).willReturn(session);
        final List<SeatDTO> seatDTOList = List.of(new SeatDTO());
        given(sessionDTO.getSeatList()).willReturn(seatDTOList);
        sessionService.save(sessionDTO);
        then(sessionService).should(times(1)).save(sessionDTO);
    }

    @Test
    void saveEmptySeatList() {
        Session session = mock(Session.class);
        SessionDTO sessionDTO = mock(SessionDTO.class);
        given(sessionRepository.save(session)).willReturn(session);
        given(mapperDTO.toSession(sessionDTO)).willReturn(session);
        final List<SeatDTO> emptyList = List.of();
        final List<Seat> notEmptyList = List.of(new Seat());
        given(seatService.getAllEntities()).willReturn(notEmptyList);
        given(mapperDTO.fromSeatList(notEmptyList)).willReturn(emptyList);
        given(sessionDTO.getSeatList()).willReturn(emptyList);
        sessionService.save(sessionDTO);
        then(sessionService).should(times(1)).save(sessionDTO);
    }

    @Test
    void getAllSortedByIdAndPaginated() {
        final Pageable pageable = PageRequest.of(1, 1);
        given(sessionRepository.findAllByOrderByIdDesc(pageable)).willReturn(Page.empty());
        final int pageNumber = pageable.getPageNumber();
        final int pageSize = pageable.getPageSize();
        given(sessionService.getAllSortedByIdAndPaginated(pageNumber, pageSize)).willReturn(Page.empty());
        final Page<SessionDTO> sessionDTOPage = sessionService.getAllSortedByIdAndPaginated(pageNumber, pageSize);
        assertThat(sessionDTOPage).isNotNull();
    }

    @Test
    void getAllSortByFilmName() {
        String sort = SORT_BY_FILM_NAME;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByFilmNameDesc() {
        String sort = SORT_BY_FILM_NAME;
        String method = SORT_METHOD_DESC;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }


    @Test
    void getAllSortByFilmNameWithFilter() {
        String sort = SORT_BY_FILM_NAME;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByFilmNameDescWithFilter() {
        String sort = SORT_BY_FILM_NAME;
        String method = SORT_METHOD_DESC;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortBySeatsRemain() {
        String sort = SORT_BY_SEATS_REMAIN;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortBySeatsRemainDesc() {
        String sort = SORT_BY_SEATS_REMAIN;
        String method = SORT_METHOD_DESC;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortBySeatsRemainWithFilter() {
        String sort = SORT_BY_SEATS_REMAIN;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortBySeatsRemainDescWithFilter() {
        String sort = SORT_BY_SEATS_REMAIN;
        String method = SORT_METHOD_DESC;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByDateTime() {
        String sort = SORT_BY_DATETIME;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByDateTimeDesc() {
        String sort = SORT_BY_DATETIME;
        String method = SORT_METHOD_DESC;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByDateTimeWithFilter() {
        String sort = SORT_BY_DATETIME;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByDateTimeDescWithFilter() {
        String sort = SORT_BY_DATETIME;
        String method = SORT_METHOD_DESC;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByDefault() {
        String sort = "";
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }


    @Test
    void getAllSortByDefaultDesc() {
        String sort = "";
        String method = SORT_METHOD_DESC;
        final String filter = "";
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    @Test
    void getAllSortByDefaultWithFilter() {
        String sort = "";
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, "", filter);
        assertThat(all).isNotNull();
    }


    @Test
    void getAllSortByDefaultDescWithFilter() {
        String sort = "";
        String method = SORT_METHOD_DESC;
        final String filter = FILTER_SHOW_ONLY_AVAILABLE;
        final Page<SessionDTO> all = getSessionDTOPage(sort, method, filter);
        assertThat(all).isNotNull();
    }

    private Page<SessionDTO> getSessionDTOPage(String sort, String method, String filter) {
        Page<Session> sessionPage = mock(Page.class);
        Page<SessionDTO> sessionDTOPage = mock(Page.class);
        given(sessionRepository.findAllBySeatListSize(anyInt(), any())).willReturn(sessionPage);
        given(mapperDTO.fromSessionPage(sessionPage)).willReturn(sessionDTOPage);
        final Page<SessionDTO> all = sessionService.getAll(1, 1, filter, sort, method);
        return all;
    }

    @Test
    void deleteById() {
        willDoNothing().given(sessionRepository).deleteById(1L);
        given(sessionRepository.existsById(1L)).willReturn(true);
        sessionService.deleteById(1L);
        then(sessionService).should(times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdThrowSessionNotFoundException() {
        given(sessionRepository.existsById(1L)).willReturn(false);
        try {
            sessionService.deleteById(1L);
            fail("Should throw SessionNotFoundException");
        } catch (SessionNotFoundException e) {
        }
        then(sessionRepository).should(never()).deleteById(1L);
    }

    @Test
    void getAll() {
        List<Session> sessionList = List.of(new Session());
        given(sessionRepository.findAll()).willReturn(sessionList);
        sessionService.getAll();
        then(sessionService).should(times(1)).getAll();
        assertThat(sessionService.getAll()).isNotNull();
    }

    @Test
    void getAllAndThrowSessionListIsEmptyException() {
        List<SessionDTO> list = null;
        try {
            list = sessionService.getAll();
            fail("SessionListIsEmptyException should be thrown");
        } catch (SessionListIsEmptyException e) {
        }
        assertThat(list).isNull();
    }

    @Test
    void getById() {
        Session session = mock(Session.class);
        given(sessionRepository.findById(1L)).willReturn(Optional.of(session));
        given(mapperDTO.fromSession(session)).willReturn(mock(SessionDTO.class));
        final SessionDTO sessionDTO = sessionService.getById(1L);
        assertThat(sessionDTO).isNotNull();
    }

    @Test
    void getEntityById() {
        Session session = mock(Session.class);
        given(sessionRepository.findById(1L)).willReturn(Optional.of(session));
        final Session byId = sessionService.getEntityById(1L);
        assertThat(byId).isNotNull();
    }

    @Test
    void getFreeAndReservedSeatMap() {
        Session session = mock(Session.class);
        final Optional<Session> optional = Optional.of(session);
        given(sessionRepository.findById(1L)).willReturn(optional);
        List<Seat> freeSeatList = mock(List.class);
        List<Seat> allSeatList = mock(List.class);
        given(session.getSeatList()).willReturn(freeSeatList);
        given(seatService.getAllEntities()).willReturn(allSeatList);
        final Map<SeatDTO, Boolean> map = sessionService.getFreeAndReservedSeatMap(1L);
        assertThat(map).isNotNull();
    }

    @Test
    void isSeatFreeBySessionReturnTrue() {
        when(sessionRepository.isSeatFreeBySession(1L, 1L)).thenReturn(true);
        assertThat(sessionRepository.isSeatFreeBySession(1L, 1L)).isTrue();
    }

    @Test
    void isSeatFreeBySessionReturnFalse() {
        List<Seat> list = mock(List.class);
        List<SeatDTO> listDTO = mock(List.class);
        given(sessionRepository.isSeatListFreeBySession(list, 1L)).willReturn(false);
        given(mapperDTO.toSeatList(listDTO)).willReturn(list);
        assertThat(sessionService.isSeatListFreeBySession(listDTO, 1L)).isFalse();
    }

    @Test
    void isSeatListFreeBySessionReturnTrue() {
        List<Seat> list = mock(List.class);
        List<SeatDTO> listDTO = mock(List.class);
        given(sessionRepository.isSeatListFreeBySession(list, 1L)).willReturn(true);
        given(mapperDTO.toSeatList(listDTO)).willReturn(list);
        assertThat(sessionService.isSeatListFreeBySession(listDTO, 1L)).isTrue();
    }

    @Test
    void isSeatListFreeBySessionReturnFalse() {
        List<Seat> list = mock(List.class);
        when(sessionRepository.isSeatListFreeBySession(list, 1L)).thenReturn(false);
        assertThat(sessionRepository.isSeatListFreeBySession(list, 1L)).isFalse();
    }

    @Test
    void deleteSessionSeat() {
        willDoNothing().given(sessionRepository).deleteSessionSeat(1L, 1L);
        sessionService.deleteSessionSeat(1L, 1L);
        then(sessionService).should(times(1)).deleteSessionSeat(1L, 1L);
    }

    @Test
    void deleteSessionSeatList() {
        List<Seat> seatList = mock(List.class);
        List<SeatDTO> seatDTOList = mock(List.class);
        willDoNothing().given(sessionRepository).deleteSessionSeatList(seatList, 1L);
        given(mapperDTO.toSeatList(seatDTOList)).willReturn(seatList);
        sessionService.deleteSessionSeatList(seatDTOList, 1L);
        then(sessionService).should(times(1)).deleteSessionSeatList(seatDTOList, 1L);
    }

    @Test
    void countFreeSeats() {
        final long anyLong = anyLong();
        given(sessionRepository.countFreeSeats(anyLong)).willReturn(anyLong);
        final Long freeSeats = sessionService.countFreeSeats(anyLong);
        Assertions.assertEquals(anyLong, freeSeats);
    }
}
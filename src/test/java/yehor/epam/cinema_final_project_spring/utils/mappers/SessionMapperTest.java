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
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SessionMapperTest {

    @Spy
    @InjectMocks
    private SessionMapper sessionMapper;

    @Mock
    private MapperDtoFacade facade;

    @Mock
    private SeatMapper seatMapper;

    @Mock
    private FilmMapper filmMapper;

    @Test
    void fromSession() {
        Session session = new Session();
        Film film = mock(Film.class);
        FilmDTO filmDTO = mock(FilmDTO.class);
        List<Seat> seatList = mock(List.class);
        List<SeatDTO> seatDTOList = mock(List.class);

        session.setId(1L);
        session.setFilm(film);
        session.setTime(LocalTime.NOON);
        session.setDate(LocalDate.EPOCH);
        session.setTicketPrice(BigDecimal.TEN);
        session.setSeatList(seatList);

        given(facade.getSeatMapper()).willReturn(seatMapper);
        given(seatMapper.fromSeatList(seatList)).willReturn(seatDTOList);
        given(facade.getFilmMapper()).willReturn(filmMapper);
        given(filmMapper.fromFilm(film)).willReturn(filmDTO);
        given(film.getName()).willReturn("name");
        given(filmDTO.getName()).willReturn("name");
        final SessionDTO sessionDTO = sessionMapper.fromSession(session);

        Assertions.assertEquals(session.getId(), sessionDTO.getId());
        Assertions.assertEquals(session.getSeatList().size(), sessionDTO.getSeatList().size());
        Assertions.assertEquals(session.getFilm().getName(), sessionDTO.getFilm().getName());
        Assertions.assertEquals(session.getDate(), sessionDTO.getDate());
        Assertions.assertEquals(session.getTime(), sessionDTO.getTime());
        Assertions.assertEquals(session.getTicketPrice(), sessionDTO.getTicketPrice());
    }

    @Test
    void toSession() {
        SessionDTO dto = new SessionDTO();
        FilmDTO filmDTO = mock(FilmDTO.class);
        Film film = mock(Film.class);
        List<SeatDTO> seatDTOS = mock(List.class);
        List<Seat> seats = mock(List.class);

        dto.setId(1L);
        dto.setFilm(filmDTO);
        dto.setTime(LocalTime.NOON);
        dto.setDate(LocalDate.EPOCH);
        dto.setTicketPrice(BigDecimal.TEN);
        dto.setSeatList(seatDTOS);

        given(facade.getSeatMapper()).willReturn(seatMapper);
        given(seatMapper.toSeatList(seatDTOS)).willReturn(seats);
        given(facade.getFilmMapper()).willReturn(filmMapper);
        given(filmMapper.toFilm(filmDTO)).willReturn(film);
        given(filmDTO.getName()).willReturn("name");
        given(film.getName()).willReturn("name");
        final Session session = sessionMapper.toSession(dto);

        Assertions.assertEquals(dto.getId(), session.getId());
        Assertions.assertEquals(dto.getSeatList().size(), session.getSeatList().size());
        Assertions.assertEquals(dto.getFilm().getName(), session.getFilm().getName());
        Assertions.assertEquals(dto.getDate(), session.getDate());
        Assertions.assertEquals(dto.getTime(), session.getTime());
        Assertions.assertEquals(dto.getTicketPrice(), session.getTicketPrice());
    }

    @Test
    void fromSessionList() {
        List<Session> sessions = mock(List.class);
        final List<SessionDTO> dtoList = sessionMapper.fromSessionList(sessions);
        Assertions.assertEquals(sessions.size(), dtoList.size());
    }

    @Test
    void fromSessionPage() {
        Page<Session> sessions = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        given(sessions.getPageable()).willReturn(pageable);
        final Page<SessionDTO> sessionDTOS = sessionMapper.fromSessionPage(sessions);
        Assertions.assertEquals(sessions.getTotalElements(), sessionDTOS.getTotalElements());
    }
}
package yehor.epam.cinema_final_project_spring.utils.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.dto.SessionDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.ArrayList;
import java.util.List;
/**
 * Mapper for {@link Session} and {@link SessionDTO}
 */
@Slf4j
public class SessionMapper {
    private final MapperDtoFacade facade;

    public SessionMapper(MapperDtoFacade facade) {
        this.facade = facade;
    }

    public SessionDTO fromSession(Session session) {
        final List<SeatDTO> seatDTOList = facade.getSeatMapper().fromSeatList(session.getSeatList());
        final FilmDTO filmDTO = facade.getFilmMapper().fromFilm(session.getFilm());
        return new SessionDTO(
                session.getId(),
                session.getTicketPrice(),
                session.getDate(),
                session.getTime(),
                filmDTO,
                seatDTOList
        );
    }

    public Session toSession(SessionDTO sessionDTO) {
        final List<Seat> seatList = facade.getSeatMapper().toSeatList(sessionDTO.getSeatList());
        final Film film = facade.getFilmMapper().toFilm(sessionDTO.getFilm());
        Session session = new Session();
        session.setId(sessionDTO.getId());
        session.setTicketPrice(sessionDTO.getTicketPrice());
        session.setDate(sessionDTO.getDate());
        session.setTime(sessionDTO.getTime());
        session.setFilm(film);
        session.setSeatList(seatList);
        return session;
    }

    public List<SessionDTO> fromSessionList(List<Session> sessionList) {
        List<SessionDTO> list = new ArrayList<>();
        if (sessionList != null)
            sessionList.forEach(session -> list.add(fromSession(session)));
        return list;
    }



    public Page<SessionDTO> fromSessionPage(Page<Session> sessionPage) {
        final List<SessionDTO> sessionDTOS = fromSessionList(sessionPage.getContent());
        return new PageImpl<>(sessionDTOS, sessionPage.getPageable(), sessionPage.getTotalElements());
    }
}

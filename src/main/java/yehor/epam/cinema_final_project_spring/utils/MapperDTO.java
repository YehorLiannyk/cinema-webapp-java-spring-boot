package yehor.epam.cinema_final_project_spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import yehor.epam.cinema_final_project_spring.dto.*;
import yehor.epam.cinema_final_project_spring.entities.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MapperDTO {

    public User toUser(UserSignUpDTO userDTO) {
        final User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserRole(userDTO.getUserRole());
        user.setNotification(userDTO.getNotification());
        return user;
    }

    public UserDTO fromUser(User user) {
        UserRoleDTO userRoleDTO = fromUserRole(user.getUserRole());
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                userRoleDTO,
                user.getNotification()
        );
    }

    public User toUser(UserDTO userDTO) {
        UserRole userRole = toUserRole(userDTO.getUserRole());
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserRole(userRole);
        user.setNotification(userDTO.getNotification());
        return user;
    }

    public UserRoleDTO fromUserRole(UserRole userRole) {
        return new UserRoleDTO(userRole.getId(), userRole.getName());
    }

    public UserRole toUserRole(UserRoleDTO userRoleDTO) {
        return new UserRole(userRoleDTO.getId(), userRoleDTO.getName());
    }

    public GenreDTO fromGenre(Genre genre) {
        return new GenreDTO(genre.getId(), genre.getName());
    }

    public Genre toGenre(GenreDTO genreDTO) {
        return new Genre(genreDTO.getId(), genreDTO.getName());
    }

    public List<GenreDTO> fromGenreList(List<Genre> genreList) {
        List<GenreDTO> list = new ArrayList<>();
        if (genreList != null)
            genreList.forEach(genre -> list.add(fromGenre(genre)));
        return list;
    }

    public List<Genre> toGenreList(List<GenreDTO> genreDTOList) {
        List<Genre> list = new ArrayList<>();
        if (genreDTOList != null)
            genreDTOList.forEach(genre -> list.add(toGenre(genre)));
        return list;
    }

    public SessionDTO fromSession(Session session) {
        final List<SeatDTO> seatDTOList = fromSeatList(session.getSeatList());
        final FilmDTO filmDTO = fromFilm(session.getFilm());
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
        final List<Seat> seatList = toSeatList(sessionDTO.getSeatList());
        final Film film = toFilm(sessionDTO.getFilm());
        Session session = new Session();
        session.setId(sessionDTO.getId());
        session.setTicketPrice(sessionDTO.getTicketPrice());
        session.setDate(sessionDTO.getDate());
        session.setTime(sessionDTO.getTime());
        session.setFilm(film);
        session.setSeatList(seatList);
        return session;
    }

    public FilmDTO fromFilm(Film film) {
        Long durationInMin = film.getDuration().toMinutes();
        final List<GenreDTO> genreDTOList = fromGenreList(film.getGenreList());
        return new FilmDTO(
                film.getId(),
                film.getName(),
                film.getDescription(),
                durationInMin,
                genreDTOList,
                film.getPosterUrl()
        );
    }

    public Film toFilm(FilmDTO filmDTO) {
        Duration duration = null;
        if (filmDTO.getDuration() != null)
            duration = Duration.ofMinutes(filmDTO.getDuration());
        List<Genre> genreList = toGenreList(filmDTO.getGenreList());
        return new Film(
                filmDTO.getId(),
                filmDTO.getName(),
                filmDTO.getDescription(),
                duration,
                genreList,
                filmDTO.getPosterUrl()
        );
    }

    public Seat toSeat(SeatDTO seatDTO) {
        Seat seat = new Seat();
        seat.setId(seatDTO.getId());
        seat.setRowNo(seatDTO.getRowNo());
        seat.setPlaceNo(seatDTO.getPlaceNo());
        return seat;
    }

    public SeatDTO fromSeat(Seat seat) {
        return new SeatDTO(
                seat.getId(),
                seat.getRowNo(),
                seat.getPlaceNo()
        );
    }

    public List<SeatDTO> fromSeatList(List<Seat> seatList) {
        List<SeatDTO> list = new ArrayList<>();
        if (seatList != null)
            seatList.forEach(seat -> list.add(fromSeat(seat)));
        return list;
    }

    public List<Seat> toSeatList(List<SeatDTO> seatDTOList) {
        List<Seat> list = new ArrayList<>();
        if (seatDTOList != null)
            seatDTOList.forEach(seatDTO -> list.add(toSeat(seatDTO)));
        return list;
    }

    public List<FilmDTO> fromFilmList(List<Film> filmList) {
        List<FilmDTO> list = new ArrayList<>();
        if (filmList != null)
            filmList.forEach(film -> list.add(fromFilm(film)));
        return list;
    }

    public List<Film> toFilmList(List<FilmDTO> filmDTOS) {
        List<Film> list = new ArrayList<>();
        if (filmDTOS != null)
            filmDTOS.forEach(film -> list.add(toFilm(film)));
        return list;
    }

    public List<SessionDTO> fromSessionList(List<Session> sessionList) {
        List<SessionDTO> list = new ArrayList<>();
        if (sessionList != null)
            sessionList.forEach(session -> list.add(fromSession(session)));
        return list;
    }

    public Page<FilmDTO> fromFilmPage(Page<Film> filmPage) {
        final List<FilmDTO> filmDTOS = fromFilmList(filmPage.getContent());
        return new PageImpl<>(filmDTOS, filmPage.getPageable(), filmPage.getTotalElements());
    }

    public Page<SessionDTO> fromSessionPage(Page<Session> sessionPage) {
        final List<SessionDTO> sessionDTOS = fromSessionList(sessionPage.getContent());
        return new PageImpl<>(sessionDTOS, sessionPage.getPageable(), sessionPage.getTotalElements());
    }


    public TicketDTO fromTicket(Ticket ticket) {
        final SessionDTO session = fromSession(ticket.getSession());
        final UserDTO user = fromUser(ticket.getUser());
        final SeatDTO seat = fromSeat(ticket.getSeat());
        return new TicketDTO(
                ticket.getId(),
                session,
                user,
                seat
        );
    }


    public Ticket toTicket(TicketDTO ticketDTO) {
        final Session session = toSession(ticketDTO.getSession());
        final User user = toUser(ticketDTO.getUser());
        final Seat seat = toSeat(ticketDTO.getSeat());
        return new Ticket(
                ticketDTO.getId(),
                session,
                user,
                seat
        );
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

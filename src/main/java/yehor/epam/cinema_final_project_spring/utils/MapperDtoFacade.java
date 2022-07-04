package yehor.epam.cinema_final_project_spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.utils.mappers.*;

/**
 * Facade for DTO mapper for getting mapper realization
 */
@Slf4j
@Service
public class MapperDtoFacade {
    public FilmMapper getFilmMapper() {
        return new FilmMapper(this);
    }

    public GenreMapper getGenreMapper() {
        return new GenreMapper(this);
    }

    public SeatMapper getSeatMapper() {
        return new SeatMapper(this);
    }

    public SessionMapper getSessionMapper() {
        return new SessionMapper(this);
    }

    public TicketMapper getTicketMapper() {
        return new TicketMapper(this);
    }

    public UserMapper getUserMapper() {
        return new UserMapper(this);
    }
}

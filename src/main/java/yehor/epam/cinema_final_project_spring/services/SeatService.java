package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.entities.Seat;

import java.util.List;
/**
 * Seat Service class
 */
public interface SeatService {
    /**
     * Get all Seat entities
     * @return seat entity list
     */
    List<Seat> getAllEntities();

}

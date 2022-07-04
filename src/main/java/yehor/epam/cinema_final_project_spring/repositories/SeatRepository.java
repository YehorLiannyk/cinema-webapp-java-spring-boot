package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yehor.epam.cinema_final_project_spring.entities.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}

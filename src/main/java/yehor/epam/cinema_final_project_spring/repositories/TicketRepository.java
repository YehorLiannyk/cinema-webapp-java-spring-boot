package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yehor.epam.cinema_final_project_spring.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
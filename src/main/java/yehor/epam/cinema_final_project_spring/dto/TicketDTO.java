package yehor.epam.cinema_final_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.entities.Session;
import yehor.epam.cinema_final_project_spring.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;

    private SessionDTO session;

    private UserDTO user;

    private SeatDTO seat;
}
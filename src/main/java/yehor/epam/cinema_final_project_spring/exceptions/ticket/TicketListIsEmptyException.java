package yehor.epam.cinema_final_project_spring.exceptions.ticket;

import lombok.experimental.StandardException;
/**
 * Exception is occurred when ticket list is empty
 */
@StandardException
public class TicketListIsEmptyException extends RuntimeException {
}

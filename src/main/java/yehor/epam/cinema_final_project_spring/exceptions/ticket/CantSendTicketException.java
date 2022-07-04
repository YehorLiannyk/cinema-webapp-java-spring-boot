package yehor.epam.cinema_final_project_spring.exceptions.ticket;

import lombok.experimental.StandardException;
/**
 * Exception is occurred when cannot send ticket to user
 */
@StandardException
public class CantSendTicketException extends RuntimeException {
}

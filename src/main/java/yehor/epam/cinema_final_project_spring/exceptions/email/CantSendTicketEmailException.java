package yehor.epam.cinema_final_project_spring.exceptions.email;

import lombok.experimental.StandardException;

/**
 * Exception is occurred when cannot send ticket via email
 */
@StandardException
public class CantSendTicketEmailException extends RuntimeException {
}

package yehor.epam.cinema_final_project_spring.exceptions.seat;

import lombok.experimental.StandardException;

/**
 * Exception is occurred when user didn't choose any seat but trying to buy ticket
 */
@StandardException
public class SeatWasNotPickedException extends RuntimeException {
}

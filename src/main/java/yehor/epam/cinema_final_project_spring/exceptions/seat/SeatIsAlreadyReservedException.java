package yehor.epam.cinema_final_project_spring.exceptions.seat;

import lombok.experimental.StandardException;
/**
 * Exception is occurred when trying to reserved seat which is already reserved by someone
 */
@StandardException
public class SeatIsAlreadyReservedException extends RuntimeException {
}

package yehor.epam.cinema_final_project_spring.exceptions.film;

import lombok.experimental.StandardException;

/**
 * Exception is occurred when received film list is empty
 */
@StandardException
public class FilmListIsEmptyException extends RuntimeException {
}

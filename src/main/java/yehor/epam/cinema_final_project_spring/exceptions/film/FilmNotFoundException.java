package yehor.epam.cinema_final_project_spring.exceptions.film;

import lombok.experimental.StandardException;
/**
 * Exception is occurred when cannot find film
 */
@StandardException
public class FilmNotFoundException extends RuntimeException {
}

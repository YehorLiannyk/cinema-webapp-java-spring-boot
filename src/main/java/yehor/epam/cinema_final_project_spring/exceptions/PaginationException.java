package yehor.epam.cinema_final_project_spring.exceptions;

import lombok.experimental.StandardException;

/**
 * Exception is occurred in pagination service, especially when user try to get invalid paginated page
 */
@StandardException
public class PaginationException extends RuntimeException {
}

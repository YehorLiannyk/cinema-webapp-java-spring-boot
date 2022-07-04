package yehor.epam.cinema_final_project_spring.exceptions;

import lombok.experimental.StandardException;

/**
 * Exception is occurred when trying to call main class method but not all required component was set
 */
@StandardException
public class ComponentNotSetException extends RuntimeException {
}

package yehor.epam.cinema_final_project_spring.exceptions.user;

import lombok.experimental.StandardException;
/**
 * Exception is occurred when trying to save user with existing credentials
 */
@StandardException
public class UserAlreadyExistException extends RuntimeException {
}

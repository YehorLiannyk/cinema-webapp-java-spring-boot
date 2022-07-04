package yehor.epam.cinema_final_project_spring.exceptions.user;

import lombok.experimental.StandardException;
/**
 * Exception is occurred when trying to get role but there is no role
 */
@StandardException
public class NoRoleException extends RuntimeException {
}

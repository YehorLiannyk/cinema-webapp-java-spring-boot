package yehor.epam.cinema_final_project_spring.dto;

import lombok.Builder;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

import java.util.Set;

@Builder
public class UserDTO {
    private final Long id;
    /**
     * User's first name
     */
    private final String firstName;
    /**
     * User's second name
     */
    private final String lastName;
    /**
     * User's  email
     */
    private final String email;
    /**
     * User's phone number (non required)
     */
    private final String phoneNumber;
    /**
     * User's role
     */
    private final UserRole userRole;
    /**
     * Email notification switcher
     */
    private final Boolean notification;
}
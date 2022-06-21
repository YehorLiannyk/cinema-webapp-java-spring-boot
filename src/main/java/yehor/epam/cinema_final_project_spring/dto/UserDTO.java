package yehor.epam.cinema_final_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    /**
     * User's first name
     */
    private String firstName;
    /**
     * User's second name
     */
    private String lastName;
    /**
     * User's  email
     */
    private String email;
    /**
     * User's phone number (non required)
     */
    private String phoneNumber;
    /**
     * User's role
     */
    private UserRoleDTO userRole;
    /**
     * Email notification switcher
     */
    private Boolean notification;
}
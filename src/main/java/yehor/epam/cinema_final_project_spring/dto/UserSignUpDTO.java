package yehor.epam.cinema_final_project_spring.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {
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
     * User's encrypted password
     */
    private String password;
    /**
     * User's phone number (non required)
     */
    private String phoneNumber;
    /**
     * Email notification switcher
     */
    private Boolean notification;
}
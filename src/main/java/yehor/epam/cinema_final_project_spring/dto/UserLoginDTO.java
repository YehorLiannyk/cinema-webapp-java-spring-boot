package yehor.epam.cinema_final_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    //todo: remove or use UserLoginDTO
    /**
     * User's  email
     */
    private String email;
    /**
     * User's encrypted password
     */
    private String password;
}
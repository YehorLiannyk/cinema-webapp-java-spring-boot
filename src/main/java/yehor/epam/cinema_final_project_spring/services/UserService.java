package yehor.epam.cinema_final_project_spring.services;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;

public interface UserService {
    void save(final UserSignUpDTO userSignUpDTO) throws UserAlreadyExistException;

    Long getUserIdByEmail(String email);

    User getEntityById(Long id);

    boolean doesUserExistByEmail(String email);


    Long getUserIdFromAuthentication(CustomUserDetails userDetails);
}

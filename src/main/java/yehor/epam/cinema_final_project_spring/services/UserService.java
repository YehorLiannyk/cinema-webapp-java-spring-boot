package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;

public interface UserService {
    void save(final UserSignUpDTO userSignUpDTO) throws UserAlreadyExistException;


    Long getUserIdByEmail(String email);

    boolean doesUserExistByEmail(String email);
}

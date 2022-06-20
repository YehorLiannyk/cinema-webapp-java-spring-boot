package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.UserDTO;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.UserAlreadyExistException;

public interface UserService {
    void save(final UserSignUpDTO userSignUpDTO) throws UserAlreadyExistException;

    void save(final UserDTO userDTO) throws UserAlreadyExistException;

    User getById(final long id);

    Long getUserIdByEmail(String email);

    //User getByLoginAndPass(String login, String password) throws UserNotFoundException;

    boolean doesUserExistByEmail(String email);
}

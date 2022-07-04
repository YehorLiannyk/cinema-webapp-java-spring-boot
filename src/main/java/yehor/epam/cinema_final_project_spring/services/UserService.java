package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;

/**
 * User Service class
 */
public interface UserService {
    /**
     * Save user
     *
     * @param userSignUpDTO user
     * @throws UserAlreadyExistException throws when such user already exists
     */
    void save(final UserSignUpDTO userSignUpDTO) throws UserAlreadyExistException;

    /**
     * Get user id by its email
     *
     * @param email user email
     * @return user id
     */
    Long getUserIdByEmail(String email);

    /**
     * Get user entity by its id
     *
     * @param id user id
     * @return user entity
     */
    User getEntityById(Long id);

    /**
     * Check user existing by its email
     *
     * @param email user email
     * @return true if user exist, otherwise false
     */
    boolean doesUserExistByEmail(String email);

    /**
     * Get user id from CustomUserDetails
     *
     * @param userDetails CustomUserDetails
     * @return user id
     */
    Long getUserIdFromAuthentication(CustomUserDetails userDetails);
}

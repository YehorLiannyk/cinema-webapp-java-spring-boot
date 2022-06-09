package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.entities.User;

import java.util.Optional;

public interface UserService {
    void save(final User user);

    Optional<User> getById(final long id);

    Optional<User> getByLoginAndPass(String login, String password);
}

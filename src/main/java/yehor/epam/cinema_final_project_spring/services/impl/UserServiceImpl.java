package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.UserDTO;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.repositories.UserRepository;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperDTO mapperDTO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperDTO mapperDTO) {
        this.userRepository = userRepository;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public void save(UserSignUpDTO userSignUpDTO) {
        final User user = mapperDTO.toUser(userSignUpDTO);
        userRepository.save(user);
    }

    @Override
    public void save(UserDTO userDTO) {
        final User user = mapperDTO.toUser(userDTO);
        userRepository.save(user);
    }

    @Override
    public Optional<User> getById(final long id) {
        return userRepository.findById(id);
    }

    //todo: read about userRepository.exists() method
    @Override
    public Optional<User> getByLoginAndPass(String login, String password) {
        return userRepository.findByEmailAndPassword(login, password);
    }
}

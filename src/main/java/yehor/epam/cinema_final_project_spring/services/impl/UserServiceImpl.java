package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.UserDTO;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.repositories.UserRepository;
import yehor.epam.cinema_final_project_spring.services.UserRoleService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;
import yehor.epam.cinema_final_project_spring.utils.PasswordEncrypt;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final MapperDTO mapperDTO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, MapperDTO mapperDTO) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public void save(UserSignUpDTO userSignUpDTO) {
        PasswordEncrypt encrypt = new PasswordEncrypt();
        userSignUpDTO.setUserRole(userRoleService.getUserRole());
        userSignUpDTO.setPassword(encrypt.encodePassword(userSignUpDTO.getPassword()));
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
    public User getByLoginAndPass(String login, String password) throws UserAlreadyExistException {
        final Optional<User> optional = userRepository.findByEmailAndPassword(login, password);
        return optional.orElseThrow(UserAlreadyExistException::new);
    }
}

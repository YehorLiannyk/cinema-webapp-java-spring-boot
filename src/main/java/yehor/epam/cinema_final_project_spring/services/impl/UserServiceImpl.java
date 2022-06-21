package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.UserRepository;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.UserRoleService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;
import yehor.epam.cinema_final_project_spring.utils.PasswordEncrypt;

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
        if (doesUserExistByEmail(userSignUpDTO.getEmail())) {
            throw new UserAlreadyExistException();
        }
        prepareUserSignUpDTO(userSignUpDTO);
        final User user = mapperDTO.toUser(userSignUpDTO);
        saveUserAuthentication(user);
        userRepository.save(user);
    }

    private void prepareUserSignUpDTO(UserSignUpDTO userSignUpDTO) {
        PasswordEncrypt encrypt = new PasswordEncrypt();
        userSignUpDTO.setUserRole(userRoleService.getUserRole());
        userSignUpDTO.setPassword(encrypt.encodePassword(userSignUpDTO.getPassword()));
    }

    private void saveUserAuthentication(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Override
    public Long getUserIdByEmail(String email) {
        final User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getId();
    }

/*    @Override
    public User getByLoginAndPass(String login, String password) throws UserNotFoundException {
        final Optional<User> optional = userRepository.findByEmailAndPassword(login, password);
        return optional.orElseThrow(UserNotFoundException::new);
    }*/

    @Override
    public boolean doesUserExistByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }
}

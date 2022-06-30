package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.UserRepository;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.UserRoleService;
import yehor.epam.cinema_final_project_spring.services.UserService;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;
import yehor.epam.cinema_final_project_spring.utils.PasswordEncrypt;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final MapperDtoFacade mapperDTO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, MapperDtoFacade mapperDTO) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public void save(UserSignUpDTO userSignUpDTO) {
        if (doesUserExistByEmail(userSignUpDTO.getEmail())) {
            log.debug("There is already user with email: " + userSignUpDTO.getEmail());
            throw new UserAlreadyExistException();
        }
        prepareUserSignUpDTO(userSignUpDTO);
        final User user = mapperDTO.getUserMapper().toUser(userSignUpDTO);
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

    @Override
    public User getEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    @Override
    public boolean doesUserExistByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public Long getUserIdFromAuthentication(CustomUserDetails userDetails) {
        if (userDetails == null ) {
            log.debug("Couldn't authenticate user");
            throw new UserNotFoundException();
        }
        String email = userDetails.getUsername();
        final User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getId();
    }
}

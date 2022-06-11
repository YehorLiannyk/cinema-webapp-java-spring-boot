package yehor.epam.cinema_final_project_spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.exceptions.UserNotExistException;
import yehor.epam.cinema_final_project_spring.repositories.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotExistException {
        final Optional<User> byEmail = userRepository.findByEmail(email);
        final User user = byEmail.orElseThrow(UserNotExistException::new);
        log.warn("Return CustomUserDetails");
        return new CustomUserDetails(user);
    }
}

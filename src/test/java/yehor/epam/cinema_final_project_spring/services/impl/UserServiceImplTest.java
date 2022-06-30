package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.entities.UserRole;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserAlreadyExistException;
import yehor.epam.cinema_final_project_spring.exceptions.user.UserNotFoundException;
import yehor.epam.cinema_final_project_spring.repositories.UserRepository;
import yehor.epam.cinema_final_project_spring.security.CustomUserDetails;
import yehor.epam.cinema_final_project_spring.services.UserRoleService;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;
import yehor.epam.cinema_final_project_spring.utils.mappers.FilmMapper;
import yehor.epam.cinema_final_project_spring.utils.mappers.UserMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Spy
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private MapperDtoFacade mapperDTO;

    @Test
    void save() {
        UserSignUpDTO userDTO = mock(UserSignUpDTO.class);
        User user = mock(User.class);
        UserRole userRole = mock(UserRole.class);
        UserMapper userMapper = mock(UserMapper.class);
        given(userDTO.getPassword()).willReturn("");
        given(mapperDTO.getUserMapper()).willReturn(userMapper);
        given(user.getUserRole()).willReturn(userRole);
        given(user.getUserRole().getName()).willReturn("userRole");
        given(userRepository.save(user)).willReturn(user);
        given(mapperDTO.getUserMapper().toUser(userDTO)).willReturn(user);
        userService.save(userDTO);
        then(userService).should(times(1)).save(userDTO);
    }

    @Test
    void saveThrowUserAlreadyExistException() {
        UserSignUpDTO userDTO = mock(UserSignUpDTO.class);
        User user = mock(User.class);
        given(userRepository.existsUserByEmail(userDTO.getEmail())).willReturn(true);
        try {
            userService.save(userDTO);
            fail("UserAlreadyExistException should be thrown");
        } catch (UserAlreadyExistException e) {
        }
        then(userRepository).should(never()).save(user);
    }

    @Test
    void getUserIdByEmail() {
        final String anyString = "";
        User user = mock(User.class);
        given(userRepository.findByEmail(anyString)).willReturn(Optional.of(user));
        assertEquals(user.getId(), userService.getUserIdByEmail(anyString));
    }

    @Test
    void getEntityById() {
        User user = mock(User.class);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        assertEquals(user, userService.getEntityById(1L));
    }

    @Test
    void doesUserExistByEmailReturnTrue() {
        final String email = "";
        given(userRepository.existsUserByEmail(email)).willReturn(true);
        assertTrue(userService.doesUserExistByEmail(email));
    }

    @Test
    void doesUserExistByEmailReturnFalse() {
        final String email = "";
        given(userRepository.existsUserByEmail(email)).willReturn(false);
        assertFalse(userService.doesUserExistByEmail(email));
    }

    @Test
    void getUserIdFromAuthentication() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        User user = mock(User.class);
        final String email = "";
        given(userDetails.getUsername()).willReturn(email);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        assertEquals(user.getId(), userService.getUserIdFromAuthentication(userDetails));
    }

    @Test
    void getUserIdFromAuthenticationThrowUserNotFoundException() {
        CustomUserDetails userDetails = null;
        try {
            userService.getUserIdFromAuthentication(userDetails);
            fail("UserNotFoundException should be thrown");
        } catch (UserNotFoundException e) {
        }
        then(userRepository).should(never()).findByEmail("");
    }
}
package yehor.epam.cinema_final_project_spring.utils.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.dto.UserDTO;
import yehor.epam.cinema_final_project_spring.dto.UserRoleDTO;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Spy
    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private UserRole userRole;

    @Mock
    private UserRoleDTO userRoleDTO;

    @Test
    void toUser() {
        User user = new User();

        user.setUserRole(userRole);
        user.setNotification(true);
        user.setPhoneNumber("380678845634");
        user.setEmail("email@email.com");
        user.setLastName("lName");
        user.setFirstName("fName");
        user.setId(1L);

        final UserDTO userDTO = userMapper.fromUser(user);

        Assertions.assertEquals(user.getId(), userDTO.getId());
        Assertions.assertEquals(user.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(user.getNotification(), userDTO.getNotification());
        Assertions.assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        Assertions.assertEquals(user.getLastName(), userDTO.getLastName());
        Assertions.assertEquals(user.getFirstName(), userDTO.getFirstName());
    }

    @Test
    void fromUser() {
        UserDTO dto = new UserDTO();

        dto.setUserRole(userRoleDTO);
        dto.setNotification(true);
        dto.setPhoneNumber("380678845634");
        dto.setEmail("email@email.com");
        dto.setLastName("lName");
        dto.setFirstName("fName");
        dto.setId(1L);

        final User user = userMapper.toUser(dto);

        Assertions.assertEquals(dto.getId(), user.getId());
        Assertions.assertEquals(dto.getEmail(), user.getEmail());
        Assertions.assertEquals(dto.getNotification(), user.getNotification());
        Assertions.assertEquals(dto.getPhoneNumber(), user.getPhoneNumber());
        Assertions.assertEquals(dto.getLastName(), user.getLastName());
        Assertions.assertEquals(dto.getFirstName(), user.getFirstName());
    }

    @Test
    void toUserFromSignup() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();

        userSignUpDTO.setNotification(true);
        userSignUpDTO.setPhoneNumber("380678845634");
        userSignUpDTO.setEmail("email@email.com");
        userSignUpDTO.setLastName("lName");
        userSignUpDTO.setFirstName("fName");
        userSignUpDTO.setPassword("pass");

        final User user = userMapper.toUser(userSignUpDTO);

        Assertions.assertEquals(userSignUpDTO.getEmail(), user.getEmail());
        Assertions.assertEquals(userSignUpDTO.getNotification(), user.getNotification());
        Assertions.assertEquals(userSignUpDTO.getPhoneNumber(), user.getPhoneNumber());
        Assertions.assertEquals(userSignUpDTO.getPassword(), user.getPassword());
        Assertions.assertEquals(userSignUpDTO.getLastName(), user.getLastName());
        Assertions.assertEquals(userSignUpDTO.getFirstName(), user.getFirstName());
    }

    @Test
    void fromUserRole() {
        UserRole userRole = new UserRole(1L, "role");
        final UserRoleDTO roleDTO = userMapper.fromUserRole(userRole);
        Assertions.assertEquals(userRole.getName(), roleDTO.getName());
        Assertions.assertEquals(userRole.getId(), roleDTO.getId());

    }

    @Test
    void toUserRole() {
        UserRoleDTO roleDTO = new UserRoleDTO(1L, "role");
        final UserRole role = userMapper.toUserRole(roleDTO);
        Assertions.assertEquals(role.getName(), roleDTO.getName());
        Assertions.assertEquals(role.getId(), roleDTO.getId());
    }
}
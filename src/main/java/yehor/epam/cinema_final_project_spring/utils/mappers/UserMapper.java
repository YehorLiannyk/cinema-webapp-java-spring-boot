package yehor.epam.cinema_final_project_spring.utils.mappers;

import lombok.extern.slf4j.Slf4j;
import yehor.epam.cinema_final_project_spring.dto.UserDTO;
import yehor.epam.cinema_final_project_spring.dto.UserRoleDTO;
import yehor.epam.cinema_final_project_spring.dto.UserSignUpDTO;
import yehor.epam.cinema_final_project_spring.entities.User;
import yehor.epam.cinema_final_project_spring.entities.UserRole;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

/**
 * Mapper for {@link User} and {@link UserDTO}
 */
@Slf4j
public class UserMapper {
    private final MapperDtoFacade facade;

    public UserMapper(MapperDtoFacade facade) {
        this.facade = facade;
    }

    public User toUser(UserSignUpDTO userDTO) {
        final User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserRole(userDTO.getUserRole());
        user.setNotification(userDTO.getNotification());
        return user;
    }

    public UserDTO fromUser(User user) {
        UserRoleDTO userRoleDTO = fromUserRole(user.getUserRole());
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                userRoleDTO,
                user.getNotification()
        );
    }

    public User toUser(UserDTO userDTO) {
        UserRole userRole = toUserRole(userDTO.getUserRole());
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserRole(userRole);
        user.setNotification(userDTO.getNotification());
        return user;
    }

    public UserRoleDTO fromUserRole(UserRole userRole) {
        return new UserRoleDTO(userRole.getId(), userRole.getName());
    }

    public UserRole toUserRole(UserRoleDTO userRoleDTO) {
        return new UserRole(userRoleDTO.getId(), userRoleDTO.getName());
    }
}

package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.entities.UserRole;
import yehor.epam.cinema_final_project_spring.exceptions.user.NoRoleException;
import yehor.epam.cinema_final_project_spring.repositories.UserRoleRepository;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {
    @Spy
    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Test
    void getUserRole() {
        final String userRoleName = "ROLE_" + Constants.USER_ROLE;
        UserRole userRole = new UserRole(1L, userRoleName);
        doReturn(Optional.of(userRole)).when(userRoleRepository).findUserRoleByName(userRoleName);
        Assertions.assertEquals(userRole, userRoleService.getUserRole());
    }

    @Test
    void getAdminRole() {
        final String userRoleName = "ROLE_" + Constants.ADMIN_ROLE;
        UserRole userRole = new UserRole(1L, userRoleName);
        doReturn(Optional.of(userRole)).when(userRoleRepository).findUserRoleByName(userRoleName);
        Assertions.assertEquals(userRole, userRoleService.getAdminRole());
    }
}
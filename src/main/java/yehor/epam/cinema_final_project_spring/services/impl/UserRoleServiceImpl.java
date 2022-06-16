package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.entities.UserRole;
import yehor.epam.cinema_final_project_spring.exceptions.NoRoleException;
import yehor.epam.cinema_final_project_spring.repositories.UserRoleRepository;
import yehor.epam.cinema_final_project_spring.services.UserRoleService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getUserRole() throws NoRoleException {
        return getRoleByName("ROLE_" + Constants.USER_ROLE);
    }

    @Override
    public UserRole getAdminRole() throws NoRoleException {
        return getRoleByName("ROLE_" + Constants.ADMIN_ROLE);
    }

    private UserRole getRoleByName(String adminRole) {
        final Optional<UserRole> userRoleByName = userRoleRepository.findUserRoleByName(adminRole);
        return userRoleByName.orElseThrow(NoRoleException::new);
    }
}

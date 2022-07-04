package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.entities.UserRole;
import yehor.epam.cinema_final_project_spring.exceptions.user.NoRoleException;

/**
 * User Role Service class
 */
public interface UserRoleService {
    /**
     * Get USER role from Database
     *
     * @return USER role
     * @throws NoRoleException throws when no USER role id Database
     */
    UserRole getUserRole() throws NoRoleException;

    /**
     * Get ADMIN role from Database
     *
     * @return ADMIN role
     * @throws NoRoleException throws when no ADMIN role id Database
     */
    UserRole getAdminRole() throws NoRoleException;
}

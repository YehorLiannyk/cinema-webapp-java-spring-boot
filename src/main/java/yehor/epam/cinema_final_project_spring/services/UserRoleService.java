package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.entities.UserRole;
import yehor.epam.cinema_final_project_spring.exceptions.user.NoRoleException;

public interface UserRoleService {
    //UserRole getByUserEmail(String email) throws NoRoleException;
    UserRole getUserRole() throws NoRoleException;
    UserRole getAdminRole() throws NoRoleException;
}

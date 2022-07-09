package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    /**
     * Get UserRole by its name
     * @param roleName name of UserRole
     * @return UserRole
     */
    Optional<UserRole> findUserRoleByName(String roleName);
}
package yehor.epam.cinema_final_project_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
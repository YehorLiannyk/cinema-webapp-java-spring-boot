package yehor.epam.cinema_final_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private Long id;

    private String name;

}
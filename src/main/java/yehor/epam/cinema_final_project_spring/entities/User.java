package yehor.epam.cinema_final_project_spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * User's first name
     */
    @Column(nullable = false)
    @Length(min = 1, max = 45)
    private String firstName;
    /**
     * User's second name
     */
    @Column(nullable = false)
    private String lastName;
    /**
     * User's  email
     */
    @Column(nullable = false, unique = true)
    private String email;
    /**
     * User's encrypted password
     */
    @Column(nullable = false)
    private String password;
    /**
     * User's phone number (non required)
     */
    private String phoneNumber;
    /**
     * User's role
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private UserRole userRole;
    /**
     * Email notification switcher
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean notification;
}
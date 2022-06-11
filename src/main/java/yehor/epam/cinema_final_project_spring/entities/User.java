package yehor.epam.cinema_final_project_spring.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * User's first name
     */
    @Column(nullable = false)
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
    @Column(unique = true)
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
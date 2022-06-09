package yehor.epam.cinema_final_project_spring.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
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
    @Column(nullable = false)
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
    @OneToOne
    @JoinColumn(name = "user_role", referencedColumnName = "id")
    private UserRole userRole;
    /**
     * Email notification switcher
     */
    private Boolean notification;
    /**
     * User's encryption
     */
    private String salt;
}
package yehor.epam.cinema_final_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import yehor.epam.cinema_final_project_spring.entities.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {
    /**
     * User's first name
     */
    @NotBlank(message = "{valid.fName.notEmpty}")
    @Length(max = MAX_FIRST_NAME_LENGTH, min = MIN_FIRST_NAME_LENGTH, message = "{valid.fName.length}")
    @Pattern(regexp = ONLY_LETTERS_PATTERN, message = "{valid.onlyLetters}")
    private String firstName;
    /**
     * User's second name
     */
    @NotBlank(message = "{valid.lName.notEmpty}")
    @Length(max = MAX_LAST_NAME_LENGTH, min = MIN_LAST_NAME_LENGTH, message = "{valid.lName.length}")
    @Pattern(regexp = ONLY_LETTERS_PATTERN, message = "{valid.onlyLetters}")
    private String lastName;
    /**
     * User's  email
     */
    @NotBlank(message = "{valid.email.notEmpty}")
    @Email(message = "{valid.email.invalid}")
    private String email;
    /**
     * User's encrypted password
     */
    @NotBlank(message = "{valid.password.notEmpty}")
    @Length(max = MAX_PASS_LENGTH, min = MIN_PASS_LENGTH, message = "{valid.password.length}")
    private String password;
    /**
     * Repeat password input
     */
    @NotBlank(message = "{valid.password.notEmpty}")
    private String passwordRepeat;
    /**
     * User's phone number (non required)
     */
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "{valid.phoneNumber.pattern}")
    private String phoneNumber;
    /**
     * User's role
     */
    private UserRole userRole;
    /**
     * Email notification switcher
     */
    private Boolean notification;
}
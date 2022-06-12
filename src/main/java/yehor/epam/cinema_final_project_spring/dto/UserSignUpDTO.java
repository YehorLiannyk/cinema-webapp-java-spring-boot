package yehor.epam.cinema_final_project_spring.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ParameterScriptAssert;
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
    @NotBlank(message = "{validation.fName.notEmpty}")
    @Length(max = MAX_FIRST_NAME_LENGTH, min = MIN_FIRST_NAME_LENGTH, message = "{validation.fName.length}")
    @Pattern(regexp = ONLY_LETTERS_PATTERN, message = "{validation.onlyLetters}")
    private String firstName;
    /**
     * User's second name
     */
    @NotBlank(message = "{validation.lName.notEmpty}")
    @Length(max = MAX_LAST_NAME_LENGTH, min = MIN_LAST_NAME_LENGTH, message = "{validation.lName.length}")
    @Pattern(regexp = ONLY_LETTERS_PATTERN, message = "{validation.onlyLetters}")
    private String lastName;
    /**
     * User's  email
     */
    @NotBlank(message = "{validation.email.notEmpty}")
    @Email(message = "{validation.email.invalid}")
    private String email;
    /**
     * User's encrypted password
     */
    @NotBlank(message = "{validation.password.notEmpty}")
    @Length(max = MAX_PASS_LENGTH, min = MIN_PASS_LENGTH, message = "{validation.password.length}")
    private String password;
    /**
     * Repeat password input
     */
    @NotBlank(message = "{validation.password.notEmpty}")
    private String passwordRepeat;
    /**
     * User's phone number (non required)
     */
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "{validation.phoneNumber.pattern}")
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
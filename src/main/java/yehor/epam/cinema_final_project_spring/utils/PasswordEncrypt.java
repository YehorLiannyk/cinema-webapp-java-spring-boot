package yehor.epam.cinema_final_project_spring.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import java.security.SecureRandom;

/**
 * Password encrypt manager class
 */
public class PasswordEncrypt {

    /**
     * Get encrypted password from plain password
     *
     * @param plainPassword plain password
     * @return encrypted password
     */
    public String encodePassword(String plainPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(Constants.PASSWORD_ENCODE_STRENGTH, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainPassword);
    }
}

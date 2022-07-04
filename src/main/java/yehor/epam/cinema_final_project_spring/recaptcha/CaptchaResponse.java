package yehor.epam.cinema_final_project_spring.recaptcha;

import lombok.Data;

/**
 * Recaptcha response object
 */
@Data
public class CaptchaResponse {

    private boolean success;
    private String challenge_ts;
    private String hostname;
}

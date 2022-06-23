package yehor.epam.cinema_final_project_spring.recaptcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ValidateCaptchaService {

    private final RestTemplate restTemplate;

    @Value("${google.recaptcha.verification.endpoint}")
    private String recaptchaEndpoint;
    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    public ValidateCaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidCaptcha(String captcha) {
        String params = "?secret=" + recaptchaSecret + "&response=" + captcha;
        String completeUrl = recaptchaEndpoint + params;
        CaptchaResponse resp = restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
        return resp != null && resp.isSuccess();
    }
}

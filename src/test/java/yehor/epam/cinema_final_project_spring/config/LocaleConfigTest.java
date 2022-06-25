package yehor.epam.cinema_final_project_spring.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.LocaleResolver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocaleConfigTest {

    @InjectMocks
    LocaleConfig localeConfig;

    @Test
    void localeResolver() {

    }

    @Test
    void localeChangeInterceptor() {
    }

    @Test
    void messageSource() {
    }

    @Test
    void getValidator() {
    }
}
package yehor.epam.cinema_final_project_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yehor.epam.cinema_final_project_spring.utils.converters.StringToFilmConverter;
import yehor.epam.cinema_final_project_spring.utils.converters.StringToGenreConverter;
import yehor.epam.cinema_final_project_spring.utils.converters.StringToSeatConverter;

/**
 * Other configuration, including formatters registration
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToGenreConverter());
        registry.addConverter(new StringToFilmConverter());
        registry.addConverter(new StringToSeatConverter());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

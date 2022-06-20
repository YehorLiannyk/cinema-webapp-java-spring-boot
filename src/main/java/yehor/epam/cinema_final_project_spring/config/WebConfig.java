package yehor.epam.cinema_final_project_spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yehor.epam.cinema_final_project_spring.utils.converters.StringToFilmConverter;
import yehor.epam.cinema_final_project_spring.utils.converters.StringToGenreConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToGenreConverter());
        registry.addConverter(new StringToFilmConverter());
    }

}

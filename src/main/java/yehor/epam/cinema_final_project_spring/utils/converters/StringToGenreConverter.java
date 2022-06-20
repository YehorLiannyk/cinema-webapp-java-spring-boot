package yehor.epam.cinema_final_project_spring.utils.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;

import java.util.Collections;
import java.util.List;

@Slf4j
public class StringToGenreConverter implements Converter<String, GenreDTO> {
    @Override
    public GenreDTO convert(String source) {
        log.debug("Source in StringToGenreConverter = " + source);
        Long id = Long.valueOf(source);
        GenreDTO genre = new GenreDTO();
        genre.setId(id);
        return genre;
    }
}

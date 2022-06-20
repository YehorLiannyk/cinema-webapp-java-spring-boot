package yehor.epam.cinema_final_project_spring.utils.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.GenreDTO;

@Slf4j
public class StringToFilmConverter implements Converter<String, FilmDTO> {
    @Override
    public FilmDTO convert(String source) {
        log.debug("Source in StringToFilmConverter = " + source);
        Long id = Long.valueOf(source);
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(id);
        return filmDTO;
    }
}

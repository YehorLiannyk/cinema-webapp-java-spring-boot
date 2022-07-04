package yehor.epam.cinema_final_project_spring.utils.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;

/**
 * Converter from string to FilmDto (by film id)
 */
@Slf4j
public class StringToFilmConverter implements Converter<String, FilmDTO> {
    /**
     * Convert from string to FilmDto (by film id)
     *
     * @param source string value
     * @return filmDto
     */
    @Override
    public FilmDTO convert(String source) {
        log.debug("Source in StringToFilmConverter = " + source);
        Long id = Long.valueOf(source);
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(id);
        return filmDTO;
    }
}

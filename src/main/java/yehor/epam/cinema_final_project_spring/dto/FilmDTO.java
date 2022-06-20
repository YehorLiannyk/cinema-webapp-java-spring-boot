package yehor.epam.cinema_final_project_spring.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import yehor.epam.cinema_final_project_spring.entities.Genre;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {
    private Long id;
    /**
     * Film's name
     */
    @NotBlank(message = "{valid.film.name.notEmpty}")
    @Length(max = MAX_FILM_NAME_LENGTH, message = "{valid.film.name.length}")
    private String name;
    /**
     * Film's description (non required)
     */
    @Length(max = MAX_FILM_DESC_LENGTH, message = "{valid.film.description.length}")
    private String description;
    /**
     * Film's duration in minutes
     */
    //@NotBlank(message = "{valid.film.duration.notEmpty}")
    @Min(value = MIN_FILM_DURATION_IN_MINUTE, message = "{valid.film.duration.min}")
    @Max(value = MAX_FILM_DURATION_IN_MINUTE, message = "{valid.film.duration.max}")
    private Long duration;
    /**
     * Film's genres
     */
    private List<GenreDTO> genreList;
    /**
     * URL of Film's poster
     */
    @NotBlank(message = "{valid.film.poster.notEmpty}")
    private String posterUrl;
}

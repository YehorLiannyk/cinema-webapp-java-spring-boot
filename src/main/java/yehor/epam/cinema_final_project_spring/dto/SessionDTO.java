package yehor.epam.cinema_final_project_spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.MAX_TICKET_COST;
import static yehor.epam.cinema_final_project_spring.utils.constants.Constants.MIN_TICKET_COST;


@Data
@NoArgsConstructor
public class SessionDTO {
    private Long id;

    @Min(value = MIN_TICKET_COST, message = "{valid.session.ticketPrice.min}")
    @Max(value = MAX_TICKET_COST, message = "valid.session.ticketPrice.max")
    @NotNull(message = "{valid.session.ticketPrice.notBlank}")
    private BigDecimal ticketPrice;


    @NotNull(message = "{valid.session.date.notNull}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @FutureOrPresent(message = "{valid.session.time.invalidDate}")
    @NotNull(message = "{valid.session.time.notNull}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @NotNull(message = "{valid.session.film.notNull}")
    private FilmDTO  film;

    private List<SeatDTO> seatList;
}

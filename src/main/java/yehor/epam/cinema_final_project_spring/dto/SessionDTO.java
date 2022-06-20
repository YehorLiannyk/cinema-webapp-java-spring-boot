package yehor.epam.cinema_final_project_spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import yehor.epam.cinema_final_project_spring.annotations.MaxSessionTime;
import yehor.epam.cinema_final_project_spring.annotations.MinSessionTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @FutureOrPresent(message = "{valid.session.time.invalidDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "{valid.session.time.notNull}")
    @MaxSessionTime(message = "{valid.session.time.max}")
    @MinSessionTime(message = "{valid.session.time.min}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @NotNull(message = "{valid.session.film.notNull}")
    private FilmDTO film;

    private List<SeatDTO> seatList;
}

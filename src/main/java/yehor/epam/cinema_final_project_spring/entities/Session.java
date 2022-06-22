package yehor.epam.cinema_final_project_spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal ticketPrice;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @OneToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToMany
    @JoinTable(
            name = "session_seats",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seatList;

    private Integer seatsAmount = seatList != null ? seatList.size() : 0;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Ticket> ticketList;
}

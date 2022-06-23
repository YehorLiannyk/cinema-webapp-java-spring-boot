package yehor.epam.cinema_final_project_spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rowNo;

    @Column(nullable = false)
    private Integer placeNo;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<Ticket> ticket;

/*@ManyToOne
    @JoinTable(
            name = "session_seats",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )*/
    @ManyToMany(mappedBy = "seatList")
    private List<Session> sessionList;
}

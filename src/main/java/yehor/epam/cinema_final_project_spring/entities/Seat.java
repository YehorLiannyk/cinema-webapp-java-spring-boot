package yehor.epam.cinema_final_project_spring.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

    @ManyToMany(mappedBy = "seatList")
    private List<Session> sessionList;
}

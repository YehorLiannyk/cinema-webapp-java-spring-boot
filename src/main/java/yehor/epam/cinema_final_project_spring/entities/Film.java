package yehor.epam.cinema_final_project_spring.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import javax.persistence.*;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "films", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Film's name
     */
    @Column(nullable = false, length = Constants.MAX_FILM_NAME_LENGTH)
    private String name;
    /**
     * Film's description (non required)
     */
    @Column(length = Constants.MAX_FILM_DESC_LENGTH)
    private String description;
    /**
     * Film's duration
     */
    @Column(nullable = false)
    private Duration duration;
    /**
     * Film's genres
     */
    @ManyToMany()
    @JoinTable(
            name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genreList;
    /**
     * URL of Film's poster
     */
    @Column(nullable = false)
    private String posterUrl;


    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private Collection<Session> session;

}

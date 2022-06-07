package yehor.epam.cinema_final_project_spring.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Film's name
     */
    @Column(nullable = false)
    private String name;
    /**
     * Film's description (non required)
     */
    private String description;
    /**
     * Film's duration
     */
    private Duration duration;
    /**
     * Film's genres
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genreList;
    /**
     * URL of Film's poster
     */
    private String posterUrl;

    public Long getDurationInMinutes() {
        return duration.toMinutes();
    }
}

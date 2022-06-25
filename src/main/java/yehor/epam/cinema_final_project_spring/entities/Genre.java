package yehor.epam.cinema_final_project_spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres", uniqueConstraints = {@UniqueConstraint(columnNames = {"genre_name"})})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Genre's name
     */
    @Column(name = "genre_name", nullable = false)
    private String name;
}

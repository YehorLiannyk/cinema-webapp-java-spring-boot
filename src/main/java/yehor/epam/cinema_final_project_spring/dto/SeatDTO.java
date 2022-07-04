package yehor.epam.cinema_final_project_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long id;
    /**
     * Number of row
     */
    private Integer rowNo;
    /**
     * Number of place
     */
    private Integer placeNo;
}

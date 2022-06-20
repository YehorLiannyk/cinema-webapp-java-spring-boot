package yehor.epam.cinema_final_project_spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeatDTO {
    private Long id;

    private Integer rowNo;

    private Integer placeNo;
}

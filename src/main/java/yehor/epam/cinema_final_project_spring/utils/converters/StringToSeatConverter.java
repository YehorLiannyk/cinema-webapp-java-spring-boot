package yehor.epam.cinema_final_project_spring.utils.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;

/**
 * Converter from string to SeatDto (by id, rowNo and placeNo)
 */
@Slf4j
public class StringToSeatConverter implements Converter<String, SeatDTO> {
    /**
     * Convert string to genre dto by id, row number and place number in format: 172_4_34
     *
     * @param source string
     * @return seatDto
     */
    @Override
    public SeatDTO convert(String source) {
        log.debug("Source in StringToSeatConverter = " + source);
        final String[] strings = source.split("_");
        Long id = Long.valueOf(strings[0]);
        Integer rowNo = Integer.valueOf(strings[1]);
        Integer placeNo = Integer.valueOf(strings[2]);
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setId(id);
        seatDTO.setRowNo(rowNo);
        seatDTO.setPlaceNo(placeNo);
        return seatDTO;
    }
}

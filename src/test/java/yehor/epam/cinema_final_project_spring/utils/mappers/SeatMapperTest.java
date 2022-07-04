package yehor.epam.cinema_final_project_spring.utils.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.dto.FilmDTO;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.entities.Film;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SeatMapperTest {
    @Spy
    @InjectMocks
    private SeatMapper seatMapper;

    @Mock
    private MapperDtoFacade facade;

    @Test
    void toSeat() {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setRowNo(1);
        seat.setPlaceNo(1);

        final SeatDTO seatDTO = seatMapper.fromSeat(seat);
        Assertions.assertEquals(seat.getId(), seatDTO.getId());
        Assertions.assertEquals(seat.getRowNo(), seatDTO.getRowNo());
        Assertions.assertEquals(seat.getPlaceNo(), seatDTO.getPlaceNo());
    }

    @Test
    void fromSeat() {
        SeatDTO dto = new SeatDTO();
        dto.setId(1L);
        dto.setRowNo(1);
        dto.setPlaceNo(1);

        final Seat seat = seatMapper.toSeat(dto);
        Assertions.assertEquals(dto.getId(), seat.getId());
        Assertions.assertEquals(dto.getRowNo(), seat.getRowNo());
        Assertions.assertEquals(dto.getPlaceNo(), seat.getPlaceNo());
    }

    @Test
    void fromSeatList() {
        List<Seat> seatList = mock(List.class);
        final List<SeatDTO> seatDTOS = seatMapper.fromSeatList(seatList);
        Assertions.assertEquals(seatList.size(), seatDTOS.size());
    }

    @Test
    void toSeatList() {
        List<SeatDTO> dtoList = mock(List.class);
        final List<Seat> seatList = seatMapper.toSeatList(dtoList);
        Assertions.assertEquals(dtoList.size(), seatList.size());
    }
}
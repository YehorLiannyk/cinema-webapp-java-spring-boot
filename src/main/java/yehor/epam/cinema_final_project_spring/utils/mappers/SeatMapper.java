package yehor.epam.cinema_final_project_spring.utils.mappers;

import lombok.extern.slf4j.Slf4j;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SeatMapper {
    private final MapperDtoFacade facade;

    public SeatMapper(MapperDtoFacade facade) {
        this.facade = facade;
    }

    public Seat toSeat(SeatDTO seatDTO) {
        Seat seat = new Seat();
        seat.setId(seatDTO.getId());
        seat.setRowNo(seatDTO.getRowNo());
        seat.setPlaceNo(seatDTO.getPlaceNo());
        return seat;
    }

    public SeatDTO fromSeat(Seat seat) {
        return new SeatDTO(
                seat.getId(),
                seat.getRowNo(),
                seat.getPlaceNo()
        );
    }

    public List<SeatDTO> fromSeatList(List<Seat> seatList) {
        List<SeatDTO> list = new ArrayList<>();
        if (seatList != null)
            seatList.forEach(seat -> list.add(fromSeat(seat)));
        return list;
    }

    public List<Seat> toSeatList(List<SeatDTO> seatDTOList) {
        List<Seat> list = new ArrayList<>();
        if (seatDTOList != null)
            seatDTOList.forEach(seatDTO -> list.add(toSeat(seatDTO)));
        return list;
    }
}

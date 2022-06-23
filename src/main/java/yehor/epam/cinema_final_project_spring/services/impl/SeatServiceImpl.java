package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.SeatDTO;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.exceptions.seat.SeatListIsEmptyException;
import yehor.epam.cinema_final_project_spring.repositories.SeatRepository;
import yehor.epam.cinema_final_project_spring.services.SeatService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;

@Slf4j
@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final MapperDTO mapperDTO;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository, MapperDTO mapperDTO) {
        this.seatRepository = seatRepository;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<Seat> getAllEntities() {
        return seatRepository.findAll();
    }

}

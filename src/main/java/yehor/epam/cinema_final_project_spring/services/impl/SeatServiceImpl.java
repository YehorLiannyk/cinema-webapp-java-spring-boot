package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.repositories.SeatRepository;
import yehor.epam.cinema_final_project_spring.services.SeatService;
import yehor.epam.cinema_final_project_spring.utils.MapperDtoFacade;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final MapperDtoFacade mapperDTO;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository, MapperDtoFacade mapperDTO) {
        this.seatRepository = seatRepository;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<Seat> getAllEntities() {
        return seatRepository.findAll();
    }

}

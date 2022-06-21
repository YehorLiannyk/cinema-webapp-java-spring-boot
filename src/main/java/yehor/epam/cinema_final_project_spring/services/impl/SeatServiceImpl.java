package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.repositories.SeatRepository;
import yehor.epam.cinema_final_project_spring.services.SeatService;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> getAllEntities() {
        return seatRepository.findAll();
    }
}

package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.entities.Seat;
import yehor.epam.cinema_final_project_spring.repositories.SeatRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {

    @Spy
    @InjectMocks
    private SeatServiceImpl seatService;

    @Mock
    private SeatRepository seatRepository;

    @Test
    void getAllEntities() {
        List<Seat> seatList = List.of(new Seat());
        given(seatRepository.findAll()).willReturn(seatList);
        seatService.getAllEntities();
        then(seatService).should(times(1)).getAllEntities();
        assertThat(seatService.getAllEntities()).isNotNull();
    }
}
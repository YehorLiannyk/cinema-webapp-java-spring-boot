package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.dto.TicketDTO;
import yehor.epam.cinema_final_project_spring.entities.Ticket;
import yehor.epam.cinema_final_project_spring.repositories.TicketRepository;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.utils.MapperDTO;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final MapperDTO mapperDTO;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, MapperDTO mapperDTO) {
        this.ticketRepository = ticketRepository;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<TicketDTO> getAllByUserId(long userId) {
        final List<Ticket> allByUserId = ticketRepository.findAllByUserId(userId);
        return mapperDTO.fromTicketList(allByUserId);
    }
}

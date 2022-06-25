package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.exceptions.PaginationException;
import yehor.epam.cinema_final_project_spring.services.PaginationService;

@Slf4j
@Service
public class PaginationServiceImpl implements PaginationService {
    @Override
    public void checkPaginatable(int totalPages, int page, int size) throws PaginationException {
        if (page > totalPages || page < 0) {
            log.debug("Page amount is bigger than total page amount. Params: pageNo = " + page + ", size = " + size);
            throw new PaginationException();
        }
    }
}

package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.services.PaginationService;

import static yehor.epam.cinema_final_project_spring.constants.Constants.DEFAULT_PAGING_SIZE;

@Service
public class PaginationServiceImpl implements PaginationService {
    @Override
    public int getPageAmount(long totalAmount, int pagingSize) {
        return totalAmount > pagingSize ? (int) (totalAmount / pagingSize) + 1 : 1;
    }
}

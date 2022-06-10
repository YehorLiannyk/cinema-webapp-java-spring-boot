package yehor.epam.cinema_final_project_spring.services.impl;

import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.services.PaginationService;

@Service
public class PaginationServiceImpl implements PaginationService {
    @Override
    public int getPageAmount(long totalAmount, int pagingSize) {
        if (totalAmount % 2 == 0)
            return totalAmount > pagingSize ? (int) (totalAmount / pagingSize) : 1;
        else
            return totalAmount > pagingSize ? (int) (totalAmount / pagingSize) + 1 : 1;

    }
}

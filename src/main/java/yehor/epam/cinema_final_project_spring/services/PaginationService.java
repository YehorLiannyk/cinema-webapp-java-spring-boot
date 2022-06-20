package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.exceptions.PaginationException;

public interface PaginationService {
    void checkPaginatable(int totalPages, int page, int size) throws PaginationException;
}

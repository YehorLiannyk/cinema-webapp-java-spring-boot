package yehor.epam.cinema_final_project_spring.services;

import yehor.epam.cinema_final_project_spring.exceptions.PaginationException;

/**
 * Pagination Service class
 */
public interface PaginationService {
    /**
     * Check if the received parameters are properly set and page can be formed
     *
     * @param totalPages total pages param
     * @param page       page number param
     * @param size       page size param
     * @throws PaginationException throws when total page amount is bigger than total
     */
    void checkPaginatable(int totalPages, int page, int size) throws PaginationException;
}

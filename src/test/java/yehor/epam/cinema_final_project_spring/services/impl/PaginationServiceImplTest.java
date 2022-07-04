package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import yehor.epam.cinema_final_project_spring.exceptions.PaginationException;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class PaginationServiceImplTest {

    @Spy
    private PaginationServiceImpl paginationService;

    @Test
    void checkPaginatable() {
        int total = 10;
        int page = 1;
        try {
            paginationService.checkPaginatable(total, page, 0);
        } catch (PaginationException e) {
            fail("PaginationException should not be thrown");
        }
    }

    @Test
    void checkPaginatableThrow() {
        int total = 1;
        int page = 10;
        try {
            paginationService.checkPaginatable(total, page, 0);
            fail("PaginationException should be thrown");
        } catch (PaginationException e) {
        }
    }
}
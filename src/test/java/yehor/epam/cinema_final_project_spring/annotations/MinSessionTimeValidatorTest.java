package yehor.epam.cinema_final_project_spring.annotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MinSessionTimeValidatorTest {

    @Spy
    private MinSessionTimeValidator validator;

    @Mock
    private MinSessionTime minSessionTime;

    @BeforeEach
    public void init() {
        validator.initialize(minSessionTime);
    }

    @Test
    void isValid() {
        LocalTime localTime = LocalTime.of(12, 0);
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final boolean valid = validator.isValid(localTime.plusHours(2), context);
        assertThat(valid).isTrue();
    }
}
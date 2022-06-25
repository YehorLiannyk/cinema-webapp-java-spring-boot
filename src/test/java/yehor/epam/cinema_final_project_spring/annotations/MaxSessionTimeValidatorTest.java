package yehor.epam.cinema_final_project_spring.annotations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaxSessionTimeValidatorTest {

    @Spy
    private MaxSessionTimeValidator validator;

    @Test
    void isValid() {
        /*LocalTime localTime = LocalTime.now();
        validator.initialize(spy(MaxSessionTime.class));
        when(validator.isValid(localTime, mock(ConstraintValidatorContext.class))).thenReturn(anyBoolean());*/
    }
}
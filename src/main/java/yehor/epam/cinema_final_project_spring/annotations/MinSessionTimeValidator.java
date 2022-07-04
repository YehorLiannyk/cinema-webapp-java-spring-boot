package yehor.epam.cinema_final_project_spring.annotations;

import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

/**
 * Implementation for {@link MinSessionTime} annotation
 */
public class MinSessionTimeValidator implements ConstraintValidator<MinSessionTime, LocalTime> {

    private LocalTime minTime;

    public void initialize(MinSessionTime annotation) {
        minTime = Constants.MIN_SESSION_TIME;
    }

    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(minTime);
    }
}

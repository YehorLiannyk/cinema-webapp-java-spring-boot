package yehor.epam.cinema_final_project_spring.annotations;

import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class MaxSessionTimeValidator implements ConstraintValidator<MaxSessionTime, LocalTime> {

    private LocalTime maxTime;

    public void initialize(MaxSessionTime annotation) {
        maxTime = Constants.MAX_SESSION_TIME;
    }

    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        return value != null && value.isBefore(maxTime);
    }
}

package yehor.epam.cinema_final_project_spring.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validate annotation for Session time validation
 */
@Constraint(validatedBy = MaxSessionTimeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSessionTime {
    String message() default "must be less than {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

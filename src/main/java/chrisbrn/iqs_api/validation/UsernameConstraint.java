package chrisbrn.iqs_api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
	String message() default "invalid username";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
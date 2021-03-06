package chrisbrn.iqs_api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
	String message() default "invalid password";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
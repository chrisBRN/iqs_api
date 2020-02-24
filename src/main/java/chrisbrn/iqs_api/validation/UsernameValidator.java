package chrisbrn.iqs_api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

	@Override
	public void initialize(UsernameConstraint username) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext cxt) {
		return (
			username != null &&
				username.matches("[A-Za-z0-9_]+") &&
				username.length() >= 5 &&
				username.length() <= 32
		);
	}
}
package chrisbrn.iqs_api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

	@Override
	public void initialize(PasswordConstraint password) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext cxt) {
		return (
			password != null &&
				password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).+") &&
				password.length() >= 8 &&
				password.length() <= 32
		);
	}
}
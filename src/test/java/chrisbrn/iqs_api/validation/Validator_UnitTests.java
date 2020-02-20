package chrisbrn.iqs_api.validation;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Validator_UnitTests {

	private ConstraintValidatorContext constraintValidatorContext;

	private final UsernameValidator usernameValidator = new UsernameValidator();
	private final PasswordValidator passwordValidator = new PasswordValidator();
	private final RoleValidator roleValidator = new RoleValidator();

	@Test
	public void returnsTrueWithValidUsername() {
		String validUsername = "test_username";
		assertTrue(usernameValidator.isValid(validUsername, constraintValidatorContext));
	}

	@Test
	public void returnsTrueWithValidPassword() {
		String validPassword = "test_P@ssw0rd";
		assertTrue(passwordValidator.isValid(validPassword, constraintValidatorContext));
	}

	@Test
	public void returnsTrueWithValidRole() {
		String validRole = "ADMIN";
		assertTrue(roleValidator.isValid(validRole, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_tooShort() {
		String invalidUsername_tooShort = "user";
		assertFalse(usernameValidator.isValid(invalidUsername_tooShort, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_tooLong() {
		String invalidUsername_tooLong = "1userName_1userName_1userName_1userName_1userName_1userName_1userName_1userName";
		assertFalse(usernameValidator.isValid(invalidUsername_tooLong, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_DoesNotMatchPattern() {
		String invalidUsername_noPattern = "User@N3me";
		assertFalse(usernameValidator.isValid(invalidUsername_noPattern, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_isNull() {
		assertFalse(usernameValidator.isValid(null, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_tooShort() {
		String invalidPassword_tooShort = "P@5ss";
		assertFalse(passwordValidator.isValid(invalidPassword_tooShort, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_tooLong() {
		String invalidPassword_tooLong = "P@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ss";
		assertFalse(passwordValidator.isValid(invalidPassword_tooLong, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_DoesNotMatchPattern() {
		String invalidPassword_noPattern = "password";
		assertFalse(passwordValidator.isValid(invalidPassword_noPattern, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_isNull() {
		assertFalse(passwordValidator.isValid(null, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidRole() {
		String invalidRole = "invalid";
		assertFalse(roleValidator.isValid(invalidRole, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithNullRole() {
		assertFalse(roleValidator.isValid(null, constraintValidatorContext));
	}
}
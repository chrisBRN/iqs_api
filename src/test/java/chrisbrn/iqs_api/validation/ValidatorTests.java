package chrisbrn.iqs_api.validation;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTests {

	private ConstraintValidatorContext constraintValidatorContext;

	private final UsernameValidator usernameValidator = new UsernameValidator();
	private final PasswordValidator passwordValidator = new PasswordValidator();
	private final RoleValidator roleValidator = new RoleValidator();

	private final String validUsername = "test_username";
	private final String validPassword = "test_P@ssw0rd";
	private final String validRole = "ADMIN";

	private final String invalidRole = "invalid";

	private final String invalidPassword_tooShort 	= "P@5ss";
	private final String invalidPassword_tooLong 	= "P@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ssP@5ss";
	private final String invalidPassword_noPattern 	= "password";

	private final String invalidUsername_tooShort 	= "user";
	private final String invalidUsername_tooLong 	= "1userName_1userName_1userName_1userName_1userName_1userName_1userName_1userName";
	private final String invalidUsername_noPattern 	= "User@N3me";

	@Test
	public void returnsTrueWithValidUsername(){
		assertTrue(usernameValidator.isValid(validUsername, constraintValidatorContext));
	}

	@Test
	public void returnsTrueWithValidPassword(){
		assertTrue(passwordValidator.isValid(validPassword, constraintValidatorContext));
	}

	@Test
	public void returnsTrueWithValidRole(){
		assertTrue(roleValidator.isValid(validRole, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_tooShort(){
		assertFalse(usernameValidator.isValid(invalidUsername_tooShort, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_tooLong(){
		assertFalse(usernameValidator.isValid(invalidUsername_tooLong, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_DoesNotMatchPattern(){
		assertFalse(usernameValidator.isValid(invalidUsername_noPattern, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidUsername_isNull(){
		assertFalse(usernameValidator.isValid(null, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_tooShort(){
		assertFalse(passwordValidator.isValid(invalidPassword_tooShort, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_tooLong(){
		assertFalse(passwordValidator.isValid(invalidPassword_tooLong, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_DoesNotMatchPattern(){
		assertFalse(passwordValidator.isValid(invalidPassword_noPattern, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidPassword_isNull(){
		assertFalse(passwordValidator.isValid(null, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithInvalidRole(){
		assertFalse(roleValidator.isValid(invalidRole, constraintValidatorContext));
	}

	@Test
	public void returnsFalseWithNullRole(){
		assertFalse(roleValidator.isValid(null, constraintValidatorContext));
	}

}
package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.models.Role;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthenticationUtilities {



	public boolean isEmailValid(String email) {
		return 	email != null &&
				EmailValidator.getInstance(false).isValid(email);
	}

	public boolean isValidLength(String string, int minLength, int maxLength){
		return 	maxLength >= string.length() &&
				minLength <= string.length();
	}

	public boolean isUsernameValid(String username){
		String pattern = "[A-Za-z0-9_]+";
		return 	username != null &&
				isValidLength(username, 5, 30) &&
				username.matches(pattern);
	}

	public boolean isPasswordValid(String password){
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return 	password != null  &&
				isValidLength(password, 8, 30) &&
				password.matches(pattern);
	}

	public boolean isRoleValid(String role) {
		return 	role != null &&
				Arrays.asList(Role.values()).contains(Role.valueOf(role));
	}
}

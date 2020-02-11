package chrisbrn.iqs_api.services.authentication;
import chrisbrn.iqs_api.services.authentication.privilege.Role;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationUtilities {

	public boolean isEmailPatternValid(String email) {
		return email != null &&
			EmailValidator.getInstance(false).isValid(email);
	}

	private boolean isValidLength(String string, int minLength, int maxLength) {
		return maxLength >= string.length() &&
			minLength <= string.length();
	}

	public boolean isUsernamePatternValid(String username) {
		String pattern = "[A-Za-z0-9_]+";
		return username != null &&
			isValidLength(username, 5, 30) &&
			username.matches(pattern);
	}

	public boolean isPasswordPatternValid(String password) {
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return password != null &&
			isValidLength(password, 8, 30) &&
			password.matches(pattern);
	}

	public boolean isAValidRole(String role) {

		try {
			Role.valueOf(role);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Optional<Role> getRoleFromString(String role) {

		try {
			return Optional.of(Role.valueOf(role));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}

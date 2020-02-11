package chrisbrn.iqs_api.services.authentication;
import chrisbrn.iqs_api.models.api.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

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

	public boolean isStringAValidRole(String role) {
		try {
			return Role.valueOf(role) != Role.NO_ROLE;
		} catch (Exception e) {
			return false;
		}
	}

	public Role getRoleFromString(String role) {
		if (isStringAValidRole(role)){
			return Role.valueOf(role);
		}
		return Role.NO_ROLE;
	}

	public int getHierarchyFromString(String role){
		return getRoleFromString(role).getHierarchy();
	}

	public boolean userModelMatchesExpectedPatterns(User user){
		return (user == null ||
			!isUsernamePatternValid(user.getUsername()) ||
			!isPasswordPatternValid(user.getPassword()) ||
			!isEmailPatternValid(user.getEmail()) ||
			!isStringAValidRole(user.getRole()));
	}

	public boolean roleHasRequiredMinimumOneToOnePrivilege(String role, User user) {

		int currentLevel = getHierarchyFromString(role);

		return 	currentLevel < Role.EMPLOYEE.getHierarchy() ||
				currentLevel < Role.valueOf(user.getRole()).getHierarchy();
	}

}

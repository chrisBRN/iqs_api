package chrisbrn.iqs_api.services.authentication.model;

import chrisbrn.iqs_api.models.api.LoginDetails;
import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.AuthenticationUtilities;
import chrisbrn.iqs_api.services.authentication.privilege.RoleUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BeanValidator {

	@Autowired AuthenticationUtilities authUtils;
	@Autowired RoleUtilities roleUtilities;

	public void checkLoginDetailsModel(LoginDetails details) {
		usernameCheck(details.getUsername());
		passwordCheck(details.getPassword());
	}

	public void checkUserModel(User user) {
		emptyCheck(user);
		usernameCheck(user.getUsername());
		passwordCheck(user.getPassword());
		emailCheck(user.getEmail());
		roleCheck(user.getRole());
	}

	private void sendBadResponseIfInvalid(boolean isValid, String message){
		if (!isValid) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
		}
	}

	private void emptyCheck(User user){
		sendBadResponseIfInvalid(user != null, "No Data Sent");
	}

	private void usernameCheck(String username){
		sendBadResponseIfInvalid(authUtils.isUsernamePatternValid(username), "Username Does Not Match Requirements");
	}

	private void passwordCheck(String password){
		sendBadResponseIfInvalid(authUtils.isPasswordPatternValid(password), "Password Does Not Match Requirements");
	}

	private void emailCheck(String email){
		sendBadResponseIfInvalid(authUtils.isEmailPatternValid(email), "Supplied 'Email Address' is Invalid");
	}

	private void roleCheck(String role){
		sendBadResponseIfInvalid(roleUtilities.isStringAValidRole(role), "Supplied 'Role' is Invalid");
	}

}

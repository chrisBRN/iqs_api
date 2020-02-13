package chrisbrn.iqs_api.services.authentication.preDB.model;

import chrisbrn.iqs_api.models.api.LoginDetails;
import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.preDB.AuthenticationUtilities;
import chrisbrn.iqs_api.services.authentication.preDB.privilege.RoleUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BeanValidator {

	@Autowired AuthenticationUtilities authUtils;
	@Autowired RoleUtilities roleUtilities;

	public void loginDetailsBeanIsValid(LoginDetails details) {

		boolean isValid = (
			authUtils.isUsernamePatternValid(details.getUsername()) &&
			authUtils.isPasswordPatternValid(details.getPassword())
		);

		if (!isValid) {
			throw new ResponseStatusException(
				HttpStatus.UNAUTHORIZED, "Invalid Credentials"
			);
		}
	}

	public void userBeanIsValid(User user) {

		boolean isValid = (
			user != null &&
			authUtils.isUsernamePatternValid(user.getUsername()) &&
			authUtils.isPasswordPatternValid(user.getPassword()) &&
			authUtils.isEmailPatternValid(user.getEmail()) &&
			roleUtilities.isStringAValidRole(user.getRole())
		);

		if (!isValid) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, "Invalid Data Provided"
			);
		}
	}

}

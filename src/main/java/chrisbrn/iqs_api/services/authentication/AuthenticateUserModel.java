package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.models.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserModel {

	@Autowired
	AuthenticationUtilities authenticationUtilities;

	public boolean isValid(User user){
		return (
			!(user == null) &&
				authenticationUtilities.isUsernamePatternValid(user.getUsername()) &&
				authenticationUtilities.isPasswordPatternValid(user.getPassword()) &&
				authenticationUtilities.isEmailPatternValid(user.getEmail()) &&
				authenticationUtilities.isAValidRole(user.getRole())
		);
	}
}

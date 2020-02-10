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
				authenticationUtilities.isUsernameValid(user.getUsername()) &&
				authenticationUtilities.isPasswordValid(user.getPassword()) &&
				authenticationUtilities.isEmailValid(user.getEmail()) &&
				authenticationUtilities.isRoleValid(user.getRole())
		);
	}
}

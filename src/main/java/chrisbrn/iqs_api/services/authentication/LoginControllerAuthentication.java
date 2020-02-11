package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.models.api.LoginDetails;
import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.utilities.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginControllerAuthentication {

	@Autowired AuthenticationUtilities authUtils;
	@Autowired DatabaseQuery dbQuery;
	@Autowired PasswordService pwService;

	public boolean loginDetailsMatchExpectedPatterns(LoginDetails details) {
		return 	authUtils.isUsernamePatternValid(details.getUsername()) &&
				authUtils.isPasswordPatternValid(details.getPassword());
	}

	public Optional<User> getUserIfDetailsMatchDB(LoginDetails details){

		Optional<User> user = dbQuery.getUserByUsername(details.getUsername());

		if(user.isEmpty()){
			return Optional.empty();
		}

		String providedPW = details.getPassword();
		String storedPW = user.get().getPassword();

		return pwService.passwordMatches(providedPW, storedPW) ?
			user :
			Optional.empty();
	}




}
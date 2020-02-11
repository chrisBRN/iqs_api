package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.models.api.LoginDetails;
import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.utilities.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticateLogin {

	@Autowired AuthenticationUtilities authUtils;
	@Autowired DatabaseQuery dbQuery;
	@Autowired PasswordService pwService;

	private boolean detailsMatchExpectedPatterns(LoginDetails loginDetails) {
		return 	authUtils.isUsernamePatternValid(loginDetails.getUsername()) &&
				authUtils.isPasswordPatternValid(loginDetails.getPassword());
	}

	public Optional<User> getUserIfDetailsMatchDB(LoginDetails loginDetails){

		if (!detailsMatchExpectedPatterns(loginDetails)) {
			return Optional.empty();
		}

		Optional<User> user = dbQuery.getUser(loginDetails.getUsername());

		if(user.isEmpty()){
			return Optional.empty();
		}

		String providedPW = loginDetails.getPassword();
		String storedPW = user.get().getPassword();

		return pwService.passwordMatches(providedPW, storedPW) ?
			user :
			Optional.empty();
	}
}
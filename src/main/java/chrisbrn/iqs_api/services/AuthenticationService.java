package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.models.UserRole;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

	private PasswordEncoder passwordEncoder;
	private DatabaseQueryService dbService;

	@Autowired
	public AuthenticationService(PasswordEncoder passwordEncoder, DatabaseQueryService dbService) {
		this.passwordEncoder = passwordEncoder;
		this.dbService = dbService;
	}

	public boolean areCredentialsValid(Credentials credentials) {
		Optional<User> user = dbService.getUser(credentials.getUsername());
		return (
			user.isPresent() &&
			isPasswordValid(credentials.getPassword(), user.get().getPassword())
		);
	}

	public boolean userExists(String username) {
		return dbService.doesUserExist(username);
	}

	public boolean isPasswordValid(String submittedPassword, String storedPassword) {
		return passwordEncoder.matches(submittedPassword, storedPassword);
	}

	public boolean isEmailAddressValid(String email) {
		return EmailValidator.getInstance(false).isValid(email);
	}

	public boolean validRole(String roleToCheck){

		for (UserRole role : UserRole.values()){

			if (role.name().equals(roleToCheck)){
				return true;
			}
		}
		return false;
	}
}

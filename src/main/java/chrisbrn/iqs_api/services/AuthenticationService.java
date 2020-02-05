package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// https://github.com/auth0/java-jwt

@Service
public class AuthenticationService {

	private PasswordEncoder passwordEncoder;
	private DatabaseQueryService dbService;

	@Autowired
	public AuthenticationService(PasswordEncoder passwordEncoder, DatabaseQueryService dbService) {
		this.passwordEncoder = passwordEncoder;
		this.dbService = dbService;
	}

	public Boolean areCredentialsValid(Credentials credentials) {
		Optional<String> storedPassword = dbService.getStoredPassword(credentials.getUsername());

		if (storedPassword.isEmpty()) {
			return null;
		}

		return isPasswordValid(credentials.getPassword(), storedPassword.get());
	}

	private boolean isPasswordValid(String submittedPassword, String storedPassword) {
		return passwordEncoder.matches(submittedPassword, storedPassword);
	}

	public boolean isEmailAddressValid() {
		return false;
	}
}

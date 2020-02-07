package chrisbrn.iqs_api.services.authentication;


import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthService {

	private PasswordEncoder encoder;

	public AuthService() {
		this.encoder = new BCryptPasswordEncoder(12);
	}

	public boolean isPasswordValid(Credentials supplied, User storedUser) {
		return encoder.matches(supplied.getPassword(), storedUser.getPassword());
	}

	public boolean isEmailValid(String email) {
		return EmailValidator.getInstance(false).isValid(email);
	}

	public boolean isRoleValid(String role) {
		try {
			Role.valueOf(role);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	public String generateRandomBase64Token(int byteLength) {
		// https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
		SecureRandom secureRandom = new SecureRandom();
		byte[] array = new byte[byteLength];
		secureRandom.nextBytes(array);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(array);
	}
}

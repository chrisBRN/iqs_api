package chrisbrn.iqs_api.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordService {

	PasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public String hash(String password) {
		return encoder.encode(password);
	}

	public boolean isPasswordValid(String supplied, String stored) {
		return encoder.matches(supplied, stored);
	}

	public String generate(int byteLength) {
		// https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string

		// Generates A Random Base64 Token
		SecureRandom secureRandom = new SecureRandom();
		byte[] array = new byte[byteLength];
		secureRandom.nextBytes(array);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(array);
	}
}

package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.Credentials;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

// https://github.com/auth0/java-jwt

@Service
public class AuthenticationService {

	private Algorithm algorithm;
	private JWTVerifier verifier;
	private DatabaseService dbService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationService(@Qualifier("signer") String signer, DatabaseService dbService, PasswordEncoder passwordEncoder) {
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer("ChrisBRN").build();
		this.dbService = dbService;
		this.passwordEncoder = passwordEncoder;
	}

	public String generateJWT() {

		final long anHour = 1000L * 60L * 60L;

		try {
			return JWT.create()
				.withIssuer("ChrisBRN")
				.withExpiresAt(new Date(anHour + System.currentTimeMillis()))
				.sign(algorithm);
		}
		catch (JWTCreationException exception) {
			System.err.println("JWTCreationException");
			return null;
		}
	}

	public Boolean validateJWT(String token) {

		try {
			DecodedJWT decoded = verifier.verify(token);
			return true;

		} catch (JWTVerificationException exception) {
			System.err.println("JWTVerificationFailed");
		}
		return false;
	}

	public boolean areCredentialsValid(Credentials credentials) {

		Optional<String> storedPassword = dbService.getStoredPassword(credentials.getUsername());

		return 	storedPassword.isPresent() &&
			isPasswordValid(credentials.getPassword(), storedPassword.get());
	}

	private boolean isPasswordValid(String submittedPassword, String storedPassword) {
		return passwordEncoder.matches(submittedPassword, storedPassword);
	}

	public String hashPassword(String password){
		return passwordEncoder.encode(password);
	}

}
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
import org.springframework.stereotype.Service;
import java.util.Date;

// https://github.com/auth0/java-jwt

@Service
public class AuthService {

	private String signer;
	Algorithm algorithm;
	JWTVerifier verifier;

	@Autowired
	public AuthService(@Qualifier("signer") String signer) {
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer("ChrisBRN").build();
		this.signer = signer;
	}

	public String generateJWT() {

		long anHour = 1000L * 60L * 60L;

		try {

			return JWT.create()
				.withIssuer("ChrisBRN")
				.withExpiresAt(new Date(anHour + System.currentTimeMillis()))
				.sign(algorithm);

		} catch (JWTCreationException exception) {
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

	public Boolean validateCredentials(Credentials credentials) {
		return isUsernameValid(credentials.getUsername()) &&
			isPasswordValid(credentials.getPassword());
	}

	private Boolean isUsernameValid(String username) {
		return username.equals("username");
	}

	private Boolean isPasswordValid(String password) {
		return password.equals("password");
	}

}

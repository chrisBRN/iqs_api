package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.helpers.Logs;
import chrisbrn.iqs_api.models.UserRole;
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

@Service
public class TokenService {

	private Algorithm algorithm;
	private JWTVerifier verifier;

	@Autowired
	public TokenService(@Qualifier("signer") String signer) {
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer("ChrisBRN").build();
	}

	public String generateJWT(String role) {

		final long anHour = 1000L * 60L * 60L;

		try {
			return JWT.create()
				.withIssuer("ChrisBRN")
				.withExpiresAt(new Date(anHour + System.currentTimeMillis()))
				.withClaim("role", role)
				.sign(algorithm);
		} catch (JWTCreationException exception) {
			Logs.tokenGenerationError();
			return null;
		}
	}

	public Boolean validateJWT(String token) {

		try {
			DecodedJWT decoded = verifier.verify(token);

			decoded.getClaim("role");

			return true;

		} catch (JWTVerificationException exception) {
			Logs.tokenVerificationError();
		}
		return false;
	}
}

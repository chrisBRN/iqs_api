package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.DecodedToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	private Algorithm algorithm;
	private JWTVerifier verifier;
	private final String issuer = "ChrisBRN";

	// Called each time the signer is updated via the DatabaseUpdate
	public void updateTokenParts(String signer) {
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
	}

	public String generateToken(UserDB user) {

		long hour = 1000L * 60L * 60L;
		return JWT.create()
			.withIssuer(issuer)
			.withExpiresAt(new Date(hour + System.currentTimeMillis()))
			.withClaim("role", user.getRole())
			.withClaim("username", user.getUsername())
			.sign(algorithm);
	}

	public DecodedToken getDecodedJWT(String token) {

		DecodedJWT jwt = verifier.verify(token);
		return new DecodedToken(
			jwt.getClaim("username").asString(),
			Role.valueOf(jwt.getClaim("role").asString()),
			jwt.getClaim("email").asString());
	}
}
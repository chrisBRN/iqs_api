package chrisbrn.iqs_api.services.authentication.token;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.models.ClaimsModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	private Algorithm algorithm;
	private JWTVerifier verifier;
	private final String issuer = "ChrisBRN";

	// Called each time the signer is updated via the DatabaseUpdate
	public void updateTokenParts(String signer){
		this.algorithm = Algorithm.HMAC256(signer);
		this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
	}

	public String generateToken(User user) {
		try {
			long hour = 1000L * 60L * 60L;
			return JWT.create()
						.withIssuer(issuer)
						.withExpiresAt(new Date(hour + System.currentTimeMillis()))
						.withClaim("role", user.getRole())
						.withClaim("username", user.getUsername())
						.withClaim("email", user.getEmail())
						.sign(algorithm);
		} catch (JWTCreationException exception) {
			return null;
		}
	}

	public ClaimsModel getDecodedJWT(String token) {
		try {
			DecodedJWT jwt = verifier.verify(token);
			return new ClaimsModel(
				jwt.getClaim("role").asString(),
				jwt.getClaim("username").asString(),
				jwt.getClaim("email").asString()
			);
		} catch (JWTVerificationException exception) {
			return null;
		}
	}
}

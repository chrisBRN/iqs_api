package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.models.database.UserDB;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenService_UnitTests {

	@Test
	void throws_whenNullSignerIsPassedToUpdateTokenParts() {
		TokenService tokenService = new TokenService();
		assertThrows(ResponseStatusException.class, ()-> tokenService.updateTokenParts(null));
	}

	@Test
	void throws_whenSignerHasNotBeenSet() {
		TokenService tokenService = new TokenService();
		assertThrows(ResponseStatusException.class, ()-> tokenService.generateToken(new UserDB()));
	}

	@Test
	void generatesJWTThatMatchesRegex() {
		TokenService tokenService = new TokenService();
		tokenService.updateTokenParts("secret");

		UserDB user = new UserDB();
		user.setUsername("user");
		user.setRole("role");

		assertTrue(Pattern
			.compile("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*")
			.matcher(tokenService.generateToken(user))
			.matches());
	}

	@Test
	void getDecodedJWT_ThrowsWhenGivenBadToken() {
		TokenService tokenService = new TokenService();
		tokenService.updateTokenParts("secret");

		assertThrows(ResponseStatusException.class, ()-> tokenService.getDecodedJWT("bad@Token"));
	}
}
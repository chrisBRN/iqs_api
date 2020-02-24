package chrisbrn.iqs_api.models.database;

import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.DecodedToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDBTest {

	@Test
	void matchesDecodedToken() {

		UserDB userDB = new UserDB();

		userDB.setUsername("username");
		userDB.setRole(Role.ADMIN.name());
		userDB.setEmail("a@a.com");

		DecodedToken badUsername = new DecodedToken("bad_username", Role.ADMIN, "a@a.com");
		DecodedToken badEmail = new DecodedToken("username", Role.ADMIN, "bad@bad.com");
		DecodedToken badRole = new DecodedToken("username", Role.EMPLOYEE, "a@a.com");

		assertFalse(userDB.matchesDecodedToken(badUsername));
		assertFalse(userDB.matchesDecodedToken(badEmail));
		assertFalse(userDB.matchesDecodedToken(badRole));

		DecodedToken goodToken = new DecodedToken("username", Role.ADMIN, "a@a.com");
		assertTrue(userDB.matchesDecodedToken(goodToken));
	}
}
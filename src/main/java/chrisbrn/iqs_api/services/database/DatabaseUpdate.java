package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.in.User;
import chrisbrn.iqs_api.services.authentication.TokenService;
import chrisbrn.iqs_api.services.PasswordService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUpdate {

	@Autowired private Jdbi jdbi;
	@Autowired private PasswordService passwordService;
	@Autowired private TokenService tokenService;

	public boolean updateDatabase(String SQLStatement) {
		int count = jdbi.withHandle(handle -> handle.createUpdate(SQLStatement).execute());
		return count != 0;
	}

	public boolean addUser(User user) {

		String hashedPassword = passwordService.hash(user.getPassword());

		String sql = (
			"INSERT INTO USERS " +
				"(username, password, role, email) " +
				"VALUES " +
				"(" +
				"'" + user.getUsername() + "'," +
				"'" + hashedPassword + "'," +
				"'" + user.getRole() + "'," +
				"'" + user.getEmail() + "'" +
				") " +
				"ON CONFLICT DO NOTHING;"
		);

		return updateDatabase(sql);
	}

	/**
	 * Ignores Role, Preventing Role Changes
	 */
	public boolean editSelf(String oldUsername, User newDetail) {

		String hashedPassword = passwordService.hash(newDetail.getPassword());

		String sql = (
			"UPDATE USERS " +
				"SET " +
				"username = '" + newDetail.getUsername() + "', " +
				"password = '" + hashedPassword + "', " +
				"email = '" + newDetail.getEmail() + "' " +
				"WHERE username = '" + oldUsername + "'" +
				"AND NOT EXISTS (" +
				"   SELECT 1 FROM USERS WHERE " +
				"username = '" + newDetail.getUsername() + "' LIMIT 1);"
		);

		return updateDatabase(sql);
	}

	public boolean updatePassword(String username, String password) {
		String hashedPassword = passwordService.hash(password);

		String sql = (
			"UPDATE USERS " +
				"SET " +
				"password = '" + hashedPassword + "' " +
				"WHERE username = '" + username + "';"
		);

		return updateDatabase(sql);
	}

	public boolean deleteUser(User user) {
		String sql = "DELETE FROM users WHERE username = '" + user.getUsername() + "';";
		return updateDatabase(sql);
	}

	public void updateSigner() {
//		String signer = passwordService.generate(64);
		String signer = "123";
		tokenService.updateTokenParts(signer);
		String sql = "UPDATE SIGNER SET signer = '" + signer + "';";

		updateDatabase(sql);
	}
}



package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.services.PasswordService;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUpdate {

	@Autowired private Jdbi jdbi;
	@Autowired private PasswordService pwService;
	@Autowired private TokenService tokenService;

	public boolean updateDatabase(String SQLStatement) {
		int count = jdbi.withHandle(handle -> handle.createUpdate(SQLStatement).execute());
		return count != 0;
	}

	public int updateDatabaseGetCount(String SQLStatement) {
		return jdbi.withHandle(handle -> handle.createUpdate(SQLStatement).execute());
	}

	public boolean addUser(UserIn userIn) {

		String hashedPassword = pwService.hash(userIn.getPassword());

		String sql = (
			"INSERT INTO USERS " +
				"(username, password, role, email) " +
				"VALUES " +
				"(" +
				"'" + userIn.getUsername() + "'," +
				"'" + hashedPassword + "'," +
				"'" + userIn.getRole() + "'," +
				"'" + userIn.getEmail() + "'" +
				") " +
				"ON CONFLICT DO NOTHING;"
		);

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



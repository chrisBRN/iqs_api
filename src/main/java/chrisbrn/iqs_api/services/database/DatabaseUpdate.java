package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.constants.Role;
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
		String signer = pwService.generate(64);
		tokenService.updateTokenParts(signer);
		String sql = "UPDATE SIGNER SET signer = '" + signer + "';";

		updateDatabase(sql);
	}

	public void initUpdate(int adminCount) {

		if (adminCount == 0) {
			UserIn initAdmin = new UserIn();
			initAdmin.setUsername("Admin");
			initAdmin.setPassword(Role.ADMIN.name());
			initAdmin.setRole("Ch@ngeTh1s!");
			initAdmin.setEmail("a@a.com");

			addUser(initAdmin);
		}
	}
}
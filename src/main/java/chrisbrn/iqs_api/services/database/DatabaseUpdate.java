package chrisbrn.iqs_api.services.database;


import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.token.TokenService;
import chrisbrn.iqs_api.utilities.PasswordService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUpdate {

	@Autowired private Jdbi jdbi;
	@Autowired private PasswordService passwordService;
	@Autowired private TokenService tokenService;

	public Boolean updateDatabase(String SQLStatement) {
		int count = jdbi.withHandle(handle -> handle.createUpdate(SQLStatement).execute());
		return count != 0;
	}

	public Boolean addUser(User user) {

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
				");"
		);

		return updateDatabase(sql);
	}

	public Boolean updateSigner() {
//		String signer = passwordService.generate(64);
		String signer = "123";
		tokenService.updateTokenParts(signer);
		String sql = "UPDATE SIGNER SET signer = '" + signer + "';";
		return updateDatabase(sql);
	}
}



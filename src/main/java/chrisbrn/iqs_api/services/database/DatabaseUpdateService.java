package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUpdateService {

	private Jdbi jdbi;
	PasswordEncoder passwordEncoder;

	@Autowired
	public DatabaseUpdateService(Jdbi jdbi, PasswordEncoder passwordEncoder) {
		this.jdbi = jdbi;
		this.passwordEncoder = passwordEncoder;
	}

	public Boolean updateDatabase(String SQLString) {
		int count = jdbi.withHandle(handle -> handle.createUpdate(SQLString).execute());
		return count != 0;
	}

	public String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public Boolean addUser(User user) {

		String hashedPassword = hashPassword(user.getPassword());

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
}



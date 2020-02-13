package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.api.LoginDetails;
import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.preDB.privilege.enums.Role;
import chrisbrn.iqs_api.utilities.PasswordService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseQuery {

	@Autowired private Jdbi jdbi;
	@Autowired private PasswordService pwService;

	private Optional<User> getUserFromDB(String sql) {
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapToBean(User.class)
			.findFirst());
	}

	public Optional<User> getUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "' LIMIT 1;";
		return getUserFromDB(sql);
	}

	public Optional<User> loginIfSuppliedDetailsMatchDB(LoginDetails details) {

		Optional<User> user = getUserByUsername(details.getUsername());

		if (user.isEmpty() || !pwService.passwordMatches(details.getPassword(), user.get().getPassword())) {
			return Optional.empty();
		}

		return user;
	}

	public int getUserTypeCount(Role role) {
		String sql = "SELECT COUNT(role) FROM users WHERE role = '" + role.name() + "';";
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapTo(Integer.class)
			.findOnly());
	}
}
package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.LoginDetails;
import chrisbrn.iqs_api.services.PasswordService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseQuery {

	@Autowired private Jdbi jdbi;
	@Autowired private PasswordService pwService;

	private Optional<UserDB> getUserFromDB(String sql) {
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapToBean(UserDB.class)
			.findFirst()
		);
	}

	public Optional<UserDB> getUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "' LIMIT 1;";
		return getUserFromDB(sql);
	}

	public Optional<UserDB> getUserWhereLoginDetailsMatchDB(LoginDetails loginDetails){

		Optional<UserDB> user = getUserByUsername(loginDetails.getUsername());

		if (user.isPresent() &&
			pwService.passwordMatches(loginDetails.getPassword(), user.get().getPassword())) {
			return user;
		}

		return Optional.empty();
	}


	public int getUserTypeCount(Role role) {
		String sql = "SELECT COUNT(role) FROM users WHERE role = '" + role.name() + "';";
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapTo(Integer.class)
			.findOnly());
	}
}
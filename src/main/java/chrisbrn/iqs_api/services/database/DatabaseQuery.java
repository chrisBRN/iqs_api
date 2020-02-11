package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.services.authentication.Role;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseQuery {

	@Autowired private Jdbi jdbi;

	public Optional<User> getUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "' LIMIT 1;";
		return jdbi.withHandle(handle -> handle
						.createQuery(sql)
						.mapToBean(User.class)
						.findFirst());

	}

	public Optional<User> getUserById(String userId) {
		String sql = "SELECT * FROM users WHERE id = " + userId + " LIMIT 1;";
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapToBean(User.class)
			.findFirst());
	}

	public int getUserTypeCount(Role role){
		String sql = "SELECT COUNT(role) FROM users WHERE role = '" + role.name() + "';";
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapTo(Integer.class)
			.findOnly());
	}

	public boolean userExistsByUsername(String username) {
		return getUserByUsername(username).isPresent();
	}


}

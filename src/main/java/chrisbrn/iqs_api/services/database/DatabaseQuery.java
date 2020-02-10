package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.api.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseQuery {

	@Autowired private Jdbi jdbi;

	public Optional<User> getUser(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "' LIMIT 1;";
		return jdbi.withHandle(handle -> handle
						.createQuery(sql)
						.mapToBean(User.class)
						.findFirst());

	}

	public boolean userExists(String username) {
		Optional<User> user = getUser(username);
		return user.isPresent();
	}
}

package chrisbrn.iqs_api.services.database;


import chrisbrn.iqs_api.models.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseQueryService {

	private Jdbi jdbi;

	@Autowired
	public DatabaseQueryService(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	public Optional<User> getUser(String username) {

		String sql = "SELECT * FROM users WHERE username = '" + username + "' LIMIT 1;";

		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapToBean(User.class)
			.findFirst());
	}

	public Boolean doesUserExist(String username) {

		String sql = "SELECT EXISTS (SELECT 1 FROM users WHERE username = '" + username + "');";

		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapTo(Boolean.class)
			.findOnly());
	}
}

package chrisbrn.iqs_api.services.database;


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

	public Optional<String> getStoredPassword(String username) {

		String sql = "SELECT password FROM USERS WHERE username = '" + username + "'";

		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapTo(String.class)
			.findFirst());
	}

	public Boolean usernameExists(){

		return false;
	}
}

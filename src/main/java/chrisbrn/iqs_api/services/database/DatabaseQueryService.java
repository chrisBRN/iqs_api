package chrisbrn.iqs_api.services.database;

import chrisbrn.iqs_api.models.Signer;
import chrisbrn.iqs_api.models.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseQueryService {

	private Jdbi jdbi;

	@Autowired
	public DatabaseQueryService(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	private Object getSingleObject(Class<?> cls, String sql) {
		return jdbi.withHandle(handle -> handle
			.createQuery(sql)
			.mapToBean(cls)
			.findFirst().orElse(null));
	}

	public User getUser(String username) {
		String sql = "SELECT * FROM users WHERE username = '" + username + "' LIMIT 1;";
		return (User) getSingleObject(User.class, sql);
	}

	public boolean userExists(String username) {
		User user = getUser(username);
		return user != null;
	}

	public String getSigner() {
		String sql = "SELECT signer FROM SIGNER";
		return ((Signer) getSingleObject(Signer.class, sql)).getSigner();
	}
}

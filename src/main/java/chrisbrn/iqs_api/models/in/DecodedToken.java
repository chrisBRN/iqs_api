package chrisbrn.iqs_api.models.in;

import chrisbrn.iqs_api.constants.Role;

public class DecodedToken {

	private final String username;
	private final Role role;
	private final String email;

	public DecodedToken(String username, Role role, String email) {
		this.username = username;
		this.role = role;
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
}

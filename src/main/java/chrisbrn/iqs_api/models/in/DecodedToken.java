package chrisbrn.iqs_api.models.in;

import chrisbrn.iqs_api.constants.Role;

public class DecodedToken {

	private String username;
	private Role role;
	private String email;

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

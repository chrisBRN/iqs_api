package chrisbrn.iqs_api.models.api;

import chrisbrn.iqs_api.models.HasRole;

public class DecodedToken implements HasRole {

	private String role;
	private String username;
	private String email;

	public DecodedToken(String role, String username, String email) {
		this.role = role;
		this.username = username;
		this.email = email;
	}

	public String getRole() {
		return role;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
}

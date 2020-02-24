package chrisbrn.iqs_api.models.database;

import chrisbrn.iqs_api.models.in.DecodedToken;

public class UserDB {

	private String username;
	private String role;
	private String email;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean matchesDecodedToken(DecodedToken token) {
		return token.getUsername().equals(this.getUsername()) &&
			token.getEmail().equals(this.getEmail()) &&
			token.getRole().name().equals(this.getRole());
	}


}

package chrisbrn.iqs_api.models.in;

import chrisbrn.iqs_api.validation.PasswordConstraint;
import chrisbrn.iqs_api.validation.UsernameConstraint;

public class LoginDetails {

	@UsernameConstraint
	private final String username;

	@PasswordConstraint
	private final String password;

	public LoginDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}

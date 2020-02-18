package chrisbrn.iqs_api.models.in;

import javax.validation.constraints.*;

public class LoginDetails {

	@Size(min = 5, max = 32, message = "Minimum length 5, max length 32")
	@Pattern(regexp = "[A-Za-z0-9_]+", message = "Only letters and / or numbers")
	@NotBlank(message = "Username Is Mandatory")
	private final String username;

	@Size(min = 8, max = 32, message = "Minimum length 8, max length 32")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Password must contain at least one uppercase letter, one lowercase letter, a number & a symbol")
	@NotBlank(message = "Password Is Mandatory")
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

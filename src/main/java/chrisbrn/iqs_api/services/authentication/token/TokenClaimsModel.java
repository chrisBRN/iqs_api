package chrisbrn.iqs_api.services.authentication.token;

public class TokenClaimsModel {

	private int userId;
	private String role;
	private String username;
	private String email;

	public TokenClaimsModel(int userId, String role, String username, String email) {
		this.userId = userId;
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

	public int getUserId() {
		return userId;
	}
}

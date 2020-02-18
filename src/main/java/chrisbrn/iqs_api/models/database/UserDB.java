package chrisbrn.iqs_api.models.database;

public class UserDB {

	private String username;
	private String role;
	private String email;
	private String password;
//	private boolean isValid;
//
//	public boolean isValid() {
//		return isValid;
//	}
//
//	public void setValid(boolean valid) {
//		isValid = valid;
//	}

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

}

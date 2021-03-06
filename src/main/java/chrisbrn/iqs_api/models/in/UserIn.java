package chrisbrn.iqs_api.models.in;

import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.validation.PasswordConstraint;
import chrisbrn.iqs_api.validation.RoleConstraint;
import chrisbrn.iqs_api.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import java.util.Optional;

public class UserIn {

	@UsernameConstraint
	private String username;
	@RoleConstraint
	private String role;
	@Email
	private String email;
	@PasswordConstraint
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@JsonIgnore
	public Role getRoleEnum() {
		return Role.roleFromString(this.role);
	}
}
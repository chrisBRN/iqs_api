package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.models.UserRole;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private PasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public boolean isPasswordValid(Credentials supplied, User storedUser) {
		return passwordEncoder.matches(supplied.getPassword(), storedUser.getPassword());
	}

	public boolean isEmailAddressValid(String email) {
		return EmailValidator.getInstance(false).isValid(email);
	}

	public boolean isRoleValid(String roleToCheck) {
		for (UserRole role : UserRole.values()) {
			if (role.name().equals(roleToCheck)) {
				return true;
			}
		}
		return false;
	}
}

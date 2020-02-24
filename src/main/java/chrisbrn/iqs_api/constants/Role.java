package chrisbrn.iqs_api.constants;

import java.util.Optional;

public enum Role {
	ADMIN,
	EMPLOYEE,
	CANDIDATE,
	NO_ROLE;

	public static Role roleFromString(String role) {

		try {
			return Role.valueOf(role);
		} catch (Exception e) {
			return NO_ROLE;
		}
	}
}
package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.constants.Role;
import org.springframework.stereotype.Service;

import static chrisbrn.iqs_api.constants.Role.CANDIDATE;
import static chrisbrn.iqs_api.constants.Role.NO_ROLE;

@Service
public class AuthorityService {

	public boolean canAddUser(Role takesAction, Role affectsUserOfType) {

		if (affectsUserOfType == NO_ROLE) {
			return false;
		}

		switch (takesAction) {
			case ADMIN:
				return true;
			case EMPLOYEE:
				return affectsUserOfType == CANDIDATE;
			default:
				return false;
		}
	}
}

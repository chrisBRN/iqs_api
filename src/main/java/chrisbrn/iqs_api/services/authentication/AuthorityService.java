package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.constants.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

	public boolean canAddUser(Role takesAction, Role affectsUserOfType){

		if (affectsUserOfType == Role.NO_ROLE) {
			return false;
		}

		switch (takesAction){
			case ADMIN:
				return true;
			case EMPLOYEE:
				return affectsUserOfType == Role.CANDIDATE;
			default:
				return false;
		}
	}
}

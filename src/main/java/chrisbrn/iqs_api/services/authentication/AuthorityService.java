package chrisbrn.iqs_api.services.authentication;

import chrisbrn.iqs_api.constants.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

	public boolean hasRequiredHierarchy(Role takesAction, Role affects){
		return takesAction.getHierarchy() >= affects.getHierarchy();
	}

	public boolean isAtLeast(Role atLeastThis, Role takesAction){
		return takesAction.getHierarchy() >= atLeastThis.getHierarchy();
	}

}

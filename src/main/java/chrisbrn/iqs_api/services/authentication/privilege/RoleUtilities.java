package chrisbrn.iqs_api.services.authentication.privilege;

import chrisbrn.iqs_api.services.authentication.privilege.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleUtilities {

	public boolean isStringAValidRole(String role) {
		try {
			return Role.valueOf(role) != Role.NO_ROLE;
		} catch (Exception e) {
			return false;
		}
	}

	public Role getRoleFromString(String role) {
		if (isStringAValidRole(role)){
			return Role.valueOf(role);
		}
		return Role.NO_ROLE;
	}

	public int getHierarchyFromString(String role){
		return getRoleFromString(role).getHierarchy();
	}


}

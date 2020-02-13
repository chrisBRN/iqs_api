package chrisbrn.iqs_api.services.authentication.preDB.privilege;


import chrisbrn.iqs_api.models.HasRole;
import chrisbrn.iqs_api.models.api.DecodedToken;
import chrisbrn.iqs_api.services.authentication.preDB.privilege.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PrivilegeValidator {

	@Autowired RoleUtilities roleUtils;

	public void hasRequiredHIERARCHY(DecodedToken takesAction, HasRole affects){

		int currentLvl 	= roleUtils.getHierarchyFromString(takesAction.getRole());
		int requiredLvl = roleUtils.getHierarchyFromString(affects.getRole());

		sendHttpStatusIfInvalid(currentLvl >= requiredLvl);
	}

	public void isAtLeast(Role role, DecodedToken takesAction){
		sendHttpStatusIfInvalid(roleUtils.getRoleFromString(takesAction.getRole()) == role);
	}

	private void sendHttpStatusIfInvalid(boolean isValid){
		if (!isValid) {
			throw new ResponseStatusException(
				HttpStatus.UNAUTHORIZED, "User Lacks Required Permissions, Please Contract The Administration Team If This Is Unexpected"
			);
		}
	}
}

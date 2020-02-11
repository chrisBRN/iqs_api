package chrisbrn.iqs_api.services.authentication.privilege;

import chrisbrn.iqs_api.services.authentication.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatePrivilege {

	@Autowired AuthenticationUtilities utils;

	public boolean hasOneToOnePermission(String claimedRole, String affectsRole) {

		Optional<Role> claimed = utils.getRoleFromString(claimedRole);
		Optional<Role> affects = utils.getRoleFromString(affectsRole);

		boolean c = claimed.isPresent();
		boolean a = affects.isPresent();
		boolean ab = hasRoleHierarchy(claimed.get(), affects.get());

		return 	claimed.isPresent() &&
				affects.isPresent() &&
				hasRoleHierarchy(claimed.get(), affects.get());
	}

	public boolean hasOneToSelf(String claimedRole, String affectsRole) {

		Optional<Role> claimed = utils.getRoleFromString(claimedRole);
		Optional<Role> affects = utils.getRoleFromString(affectsRole);

		boolean c = claimed.isPresent();
		boolean a = affects.isPresent();

		return 	claimed.isPresent() &&
			affects.isPresent() &&
			hasRoleMatching(claimed.get(), affects.get());
	}




	private boolean hasRoleHierarchy(Role claimed, Role affects) {

		int c = claimed.getHierarchy();
		int a = affects.getHierarchy();

		boolean ab = claimed.getHierarchy() >= affects.getHierarchy();

		return claimed.getHierarchy() >= affects.getHierarchy();
	}

	private boolean hasRoleMatching(Role claimed, Role affects) {
		return claimed.getHierarchy() == affects.getHierarchy();
	}
}

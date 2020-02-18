package chrisbrn.iqs_api.contollers;



import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.services.HttpService;
import chrisbrn.iqs_api.services.authentication.AuthorityService;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class UserManagementController {

	@Autowired HttpService httpService;
	@Autowired AuthorityService authService;
	@Autowired DatabaseUpdate dbUpdate;

	@RequestMapping(value = "/add-user", method = POST, consumes = "application/json", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@RequestHeader("token") DecodedToken token, @Valid @RequestBody UserIn userIn) {

		boolean hasHierarchy 		= authService.hasRequiredHierarchy(token.getRole(), userIn.getRoleEnum());
		boolean isAtLeastEmployee 	= authService.isAtLeast(Role.EMPLOYEE, token.getRole());

		if (!hasHierarchy || !isAtLeastEmployee) {
			return httpService.insufficientAuthority();
		}

		boolean didAdd = dbUpdate.addUser(userIn);


		return didAdd ?
			httpService.addUserSuccess(userIn) :
			httpService.addUserFailure();
	}
}

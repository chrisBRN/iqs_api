package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.services.HttpService;
import chrisbrn.iqs_api.services.authentication.AuthorityService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.logging.Level;

import static chrisbrn.iqs_api.services.Log.LOGGER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class AddUserController {

	@Autowired HttpService httpService;
	@Autowired AuthorityService authService;
	@Autowired DatabaseUpdate dbUpdate;
	@Autowired DatabaseQuery dbQuery;

	@RequestMapping(value = "/add-user", method = POST, consumes = "application/json", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@RequestHeader("token") DecodedToken token, @Valid @RequestBody UserIn userIn) {

		Role takesAction = token.getRole();
		Role affects = userIn.getRoleEnum();

		if (!authService.canAddUser(takesAction, affects)) {
			return httpService.insufficientAuthority();
		}

		if (affects.equals(Role.ADMIN) && !dbQuery.storedRoleMatchesTokenRole(token)){
			// If the user being added has an Admin Role, an additional check is performed
			// Ensuring the token credentials match those on the database
			LOGGER.log(Level.SEVERE, "Potential Escalation of Privilege Attack");
			return httpService.badToken();
		}

		return dbUpdate.addUser(userIn) ?
			httpService.addUserSuccess(userIn) :
			httpService.addUserFailure();
	}
}

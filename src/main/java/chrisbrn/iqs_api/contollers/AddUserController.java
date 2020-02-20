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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class AddUserController {

	@Autowired private HttpService httpService;
	@Autowired private AuthorityService authService;
	@Autowired private DatabaseUpdate dbUpdate;
	@Autowired private DatabaseQuery dbQuery;

	@RequestMapping(value = "/add-user", method = POST, consumes = "application/json", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@RequestHeader("token") DecodedToken token, @Valid @RequestBody UserIn userIn) {

		Role actor = token.getRole();
		Role affects = userIn.getRoleEnum();

		if (!authService.canAddUser(actor, affects)) {
			return httpService.insufficientAuthority();
		}

		if (affects == Role.ADMIN && !dbQuery.storedUserMatchesTokenUser(token)) {
			return httpService.tokenDBMismatch();
		}

		return dbUpdate.addUser(userIn) ?
			httpService.addUserSuccess(userIn) :
			httpService.addUserFailure();
	}
}

package chrisbrn.iqs_api.contollers;

import chrisbrn.iqs_api.models.api.User;
import chrisbrn.iqs_api.models.ClaimsModel;
import chrisbrn.iqs_api.services.authentication.privilege.AuthenticatePrivilege;
import chrisbrn.iqs_api.services.authentication.AuthenticateUserModel;
import chrisbrn.iqs_api.services.authentication.token.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQuery;
import chrisbrn.iqs_api.services.database.DatabaseUpdate;
import chrisbrn.iqs_api.utilities.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class UserManagementController {

	@Autowired AuthenticateUserModel authenticateUserModel;
	@Autowired TokenService tokenService;
	@Autowired DatabaseQuery dbQuery;
	@Autowired DatabaseUpdate dbUpdate;
	@Autowired AuthenticatePrivilege authPermission;

	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		ClaimsModel claims = tokenService.getDecodedJWT(token);

		if (claims == null) {
			return HttpResponse.forbidden();
		}

		if (!authenticateUserModel.isValid(user)) {
			return HttpResponse.badDto();
		}

		if (!authPermission.hasOneToOnePermission(claims.getRole(), user.getRole())) {
			return HttpResponse.unauthorised();
		}

		// DB Check
		if (dbQuery.userExists(user.getUsername())) {
			return HttpResponse.bad("Username Taken");
		}

		return dbUpdate.addUser(user) ?
			HttpResponse.ok("User Added") :
			HttpResponse.bad("Failed To Add User");
	}



	@RequestMapping(value = "/edit-user", method = POST)
	public ResponseEntity<String> editUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		



		return HttpResponse.bad("!");
	}


}

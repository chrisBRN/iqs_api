package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.AuthenticationService;
import chrisbrn.iqs_api.services.TokenService;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import chrisbrn.iqs_api.services.database.DatabaseUpdateService;
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

	TokenService tokenService;
	AuthenticationService authService;
	DatabaseUpdateService dbUpdateService;
	DatabaseQueryService dbQueryService;

	@Autowired
	public UserManagementController(TokenService tokenService, AuthenticationService authService, DatabaseUpdateService dbUpdateService, DatabaseQueryService dbQueryService) {
		this.tokenService = tokenService;
		this.authService = authService;
		this.dbUpdateService = dbUpdateService;
		this.dbQueryService = dbQueryService;
	}

	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		// In Memory Checks
		if (!tokenService.validateJWT(token)) {
			return HttpResponsesKt.badRequest("Login Required");
		}
		if (!authService.isEmailAddressValid(user.getEmail())) {
			return HttpResponsesKt.badRequest("Please Enter A Valid Email Address");
		}
		if (!authService.isRoleValid(user.getRole())) {
			return HttpResponsesKt.badRequest("Please Enter A Valid Role");
		}

		// DB Check
		if (dbQueryService.userExists(user.getUsername())) {
			return HttpResponsesKt.badRequest("Username Taken");
		}

		return dbUpdateService.addUser(user) ?
			HttpResponsesKt.ok("Success - User Added") :
			HttpResponsesKt.badRequest("Failed To Add User");
	}
}

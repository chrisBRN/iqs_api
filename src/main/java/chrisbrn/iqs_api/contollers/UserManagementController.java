package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.authentication.*;

import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import chrisbrn.iqs_api.services.database.DatabaseUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class UserManagementController {

	private DatabaseUpdateService dbUpdateService;
	private DatabaseQueryService dbQueryService;
	private TokenService tokenService;
	private AuthService authService;

	@Autowired
	public UserManagementController(DatabaseUpdateService dbUpdateService,
									DatabaseQueryService dbQueryService,
									TokenService tokenService,
									AuthService authService) {

		this.dbUpdateService = dbUpdateService;
		this.dbQueryService = dbQueryService;
		this.tokenService = tokenService;
		this.authService = authService;
	}

	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {


//
//		if (!authService..hasAddCandidatePrivileges(tokenService.getDecodedJWT(token).getRole())) {
//			return HttpResponsesKt.forbidden();
//		}

		if (!authService.isEmailValid(user.getEmail())) {
			return HttpResponsesKt.badRequest("Invalid Email Address");
		}

		if (!authService.isRoleValid(user.getRole())){
			return HttpResponsesKt.badRequest("Invalid Role");
		}

		// DB Check
		if (dbQueryService.userExists(user.getUsername())) {
			return HttpResponsesKt.badRequest("Username Taken");
		}

		return dbUpdateService.addUser(user) ?
			HttpResponsesKt.ok("Success - User Added") :
			HttpResponsesKt.badRequest("Failed To Add User");
	}

	@RequestMapping(value = "/update-user", method = POST)
	public ResponseEntity<String> updateUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {


		return HttpResponsesKt.badRequest();
	}


}

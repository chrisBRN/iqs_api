package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.Credentials;
import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.AuthenticationKt;

import chrisbrn.iqs_api.services.TokenServiceKt;
import chrisbrn.iqs_api.services.database.DatabaseQueryService;
import chrisbrn.iqs_api.services.database.DatabaseUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class UserManagementController {


	DatabaseUpdateService dbUpdateService;
	DatabaseQueryService dbQueryService;

	@Autowired
	public UserManagementController(DatabaseUpdateService dbUpdateService, DatabaseQueryService dbQueryService) {
		this.dbUpdateService = dbUpdateService;
		this.dbQueryService = dbQueryService;
	}

	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		// In Memory Checks
		if (!TokenServiceKt.isValidToken(token)) {
			return HttpResponsesKt.badRequest("Login Required");
		}

		Credentials suppliedTokenCredentials = TokenServiceKt.decodeToken(token);
		Optional<User> storedUser = dbQueryService.getUser(suppliedTokenCredentials.getUsername());
//		TODO Check if these match

		if (!AuthenticationKt.isEmailAddressValid(user.getEmail())) {
			return HttpResponsesKt.badRequest("Please Enter A Valid Email Address");
		}
		if (!AuthenticationKt.isRoleValid(user.getRole())) {
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

	@RequestMapping(value = "/update-user", method = POST)
	public ResponseEntity<String> updateUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {



		return HttpResponsesKt.badRequest();
	}










}

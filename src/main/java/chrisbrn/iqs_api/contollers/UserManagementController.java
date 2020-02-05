package chrisbrn.iqs_api.contollers;


import chrisbrn.iqs_api.models.HttpResponsesKt;
import chrisbrn.iqs_api.models.User;
import chrisbrn.iqs_api.services.TokenService;
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

	@Autowired
	public UserManagementController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@RequestMapping(value = "/add-user", method = POST)
	public ResponseEntity<String> addUser(@RequestHeader(value = "token") String token, @ModelAttribute User user) {

		if(!tokenService.validateJWT(token)){
			return HttpResponsesKt.badRequest("Login Required");
		}

		// do something





		return HttpResponsesKt.ok("Success - User Added");
	}


}

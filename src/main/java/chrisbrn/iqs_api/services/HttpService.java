package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.out.LoginFailure;
import chrisbrn.iqs_api.models.out.LoginSuccess;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HttpService {

	@Autowired TokenService tokenService;

	public ResponseEntity<?> loginSuccess(UserDB user){
		return ResponseEntity.status(HttpStatus.OK).body(new LoginSuccess(tokenService.generateToken(user)));
	}

	public ResponseEntity<?> loginFailure(){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginFailure());
	}


}

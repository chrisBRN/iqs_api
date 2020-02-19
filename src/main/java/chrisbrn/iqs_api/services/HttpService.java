package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.database.UserDB;
import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.models.out.*;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HttpService {

	@Autowired TokenService tokenService;

	public ResponseEntity<?> loginSuccess(UserDB user){
		return ResponseEntity.status(HttpStatus.OK).body(new LoginSuccess(tokenService.generateToken(user)));
	}

	public ResponseEntity<?> loginFailure(){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginFailure());
	}

	public ResponseEntity<?> badToken(){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BadToken());
	}

	public ResponseEntity<?> addUserSuccess(UserIn user){
		return ResponseEntity.status(HttpStatus.OK).body(new AddUserSuccess(user));
	}

	public ResponseEntity<?> addUserFailure(){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AddUserFailure());
	}

	public ResponseEntity<?> insufficientAuthority(){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new InsufficientAuthority());
	}

	public ResponseEntity<?> mediaTypeNotSupported(){
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new MediaTypeNotSupportedError());
	}

	public ResponseEntity<?> generalError(){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GeneralExceptionError());
	}

	public ResponseEntity<?> modelValidationError(List<ModelValidationError> errors){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
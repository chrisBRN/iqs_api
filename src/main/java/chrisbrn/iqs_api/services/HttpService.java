package chrisbrn.iqs_api.services;

import chrisbrn.iqs_api.models.in.UserIn;
import chrisbrn.iqs_api.models.out.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

import static chrisbrn.iqs_api.services.Log.LOGGER;

@Service
public class HttpService {



	public ResponseEntity<?> loginSuccess(String token) {
		return ResponseEntity.status(HttpStatus.OK).body(new LoginSuccess(token));
	}

	public ResponseEntity<?> loginFailure(){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginFailure());
	}

	public ResponseEntity<?> badToken(){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BadToken());
	}

	public ResponseEntity<?> tokenDBMismatch(){
		LOGGER.log(Level.SEVERE, "Potential Escalation of Privilege Attack");
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

	public ResponseEntity<?> modelValidationError(List<ModelValidationError> errors){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}

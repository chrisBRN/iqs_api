package chrisbrn.iqs_api.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponse {

	static public ResponseEntity<String> loginSuccess(String token) {
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	static public ResponseEntity<String> ok(String message) {
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	static public ResponseEntity<String> tryAgain() {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("There Was An Error, Please Try Again");
	}

	static public ResponseEntity<String> bad(String message) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	static public ResponseEntity<String> badDto() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Data Received");
	}

	static public ResponseEntity<String> forbidden() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username Or Password Does Not Match");
	}

	static public ResponseEntity<String> unauthorised() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
	}
}

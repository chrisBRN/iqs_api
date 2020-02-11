package chrisbrn.iqs_api.models.authentication;

import chrisbrn.iqs_api.utilities.HttpResponse;
import org.springframework.http.ResponseEntity;

public class BooleanResponsePair {

	boolean result;
	ResponseEntity<String> response;

	public BooleanResponsePair(boolean result, ResponseEntity<String> response){
		this.result = result;
		this.response = response;
	}
}

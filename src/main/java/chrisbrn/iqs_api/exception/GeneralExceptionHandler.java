package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GeneralExceptionHandler {

	@Autowired HttpService httpService;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> generalExceptionHandler(Exception e) {
		return httpService.generalError();
	}
}

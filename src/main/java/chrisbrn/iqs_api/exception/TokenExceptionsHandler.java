package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.services.HttpService;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Priority;

@ControllerAdvice
public class TokenExceptionsHandler {

	@Autowired HttpService httpService;

	@ExceptionHandler(value = JWTVerificationException.class)
	public ResponseEntity<?> JWTVerificationError(JWTVerificationException e) {
		return httpService.badToken();
	}

	@ExceptionHandler(value = JWTCreationException.class)
	public ResponseEntity<?> JWTCreationError(JWTCreationException e) {
		return httpService.generalError();
	}
}

package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.models.out.ModelValidationError;
import chrisbrn.iqs_api.services.HttpService;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MediaTypeNotSupportedStatusException;

import javax.annotation.Priority;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

	@Autowired HttpService httpService;

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){

		List<ModelValidationError> errors = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()){

			String field 	= ((FieldError) error).getField();
			String rejected = (String) ((FieldError) error).getRejectedValue();
			String message 	= error.getDefaultMessage();

			if (field.equals("password")){
				rejected = "********";
			}
			errors.add(new ModelValidationError(field, rejected, message));
		}
		return httpService.modelValidationError(errors);
	}
}

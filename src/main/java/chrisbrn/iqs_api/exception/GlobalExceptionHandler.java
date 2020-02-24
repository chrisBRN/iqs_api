package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.models.out.ModelValidationError;
import chrisbrn.iqs_api.services.HttpService;
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

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

	//TODO https://dzone.com/articles/customize-error-responses-in-spring-boot

	@Autowired HttpService httpService;

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> mediaTypeNotSupportedError(HttpMediaTypeNotSupportedException e) {
		return httpService.mediaTypeNotSupported();
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

		List<ModelValidationError> errors = new ArrayList<>();

		for (ObjectError error : exception.getBindingResult().getAllErrors()) {

			String field = ((FieldError) error).getField();
			String rejected = (String) ((FieldError) error).getRejectedValue();
			String message = error.getDefaultMessage();

			if (field.equals("password")) {
				rejected = "********";
			}
			errors.add(new ModelValidationError(field, rejected, message));
		}
		return httpService.modelValidationError(errors);
	}
}
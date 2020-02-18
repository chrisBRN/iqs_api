package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class CustomExceptionHandler {

	@Autowired HttpService httpService;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> modelValidationError(MethodArgumentNotValidException exception){

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

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> modelValidationError(HttpMediaTypeNotSupportedException exception) {
		return httpService.mediaTypeNotSupported(new MediaTypeNotSupportedError());
	}


}

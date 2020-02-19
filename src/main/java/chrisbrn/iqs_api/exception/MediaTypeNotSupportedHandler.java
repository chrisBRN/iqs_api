package chrisbrn.iqs_api.exception;

import chrisbrn.iqs_api.services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MediaTypeNotSupportedStatusException;

import javax.annotation.Priority;

@ControllerAdvice
public class MediaTypeNotSupportedHandler {

	@Autowired HttpService httpService;

	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> mediaTypeNotSupportedError(HttpMediaTypeNotSupportedException e) {
		return httpService.mediaTypeNotSupported();
	}
}

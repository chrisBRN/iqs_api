package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginSuccess implements Serializable {

	private final HttpStatus status = HttpStatus.OK;
	private final Integer statusCode = status.value();
	private final String information = "login success";
	private final String token;

	public LoginSuccess(String token) {
		this.token = token;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getInformation() {
		return information;
	}

	public String getToken() {
		return token;
	}
}
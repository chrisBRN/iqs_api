package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginSuccess implements Serializable {

	private HttpStatus status = HttpStatus.OK;
	private Integer statusCode = status.value();
	private String information = "login success";
	private String token;

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

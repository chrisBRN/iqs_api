package chrisbrn.iqs_api.models.out;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BadToken implements Serializable {

	private HttpStatus status = HttpStatus.FORBIDDEN;
	private Integer statusCode = status.value();
	private String information = "please log in";

	public HttpStatus getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getInformation() {
		return information;
	}
}

package chrisbrn.iqs_api.models.out;

import chrisbrn.iqs_api.models.in.UserIn;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddUserSuccess implements Serializable {

	private HttpStatus status = HttpStatus.OK;
	private Integer statusCode = status.value();
	private String information = "user added";

	private UserIn user;

	public AddUserSuccess(UserIn user){
		this.user = user;
		user.setPassword("********");
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
}

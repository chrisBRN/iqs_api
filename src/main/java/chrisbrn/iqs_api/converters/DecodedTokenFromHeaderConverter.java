package chrisbrn.iqs_api.converters;

import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

@Component
public class DecodedTokenFromHeaderConverter implements Converter<String, DecodedToken> {

	@Autowired private TokenService tokenService;

	@Override
	public DecodedToken convert(String token){

		boolean matches = Pattern.compile("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*").matcher(token).matches();

		if(!matches){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "please login");
		}
		return tokenService.getDecodedJWT(token);
	}
}

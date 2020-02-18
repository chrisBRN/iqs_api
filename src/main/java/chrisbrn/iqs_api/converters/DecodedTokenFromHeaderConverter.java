package chrisbrn.iqs_api.converters;

import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

@Component
public class DecodedTokenFromHeaderConverter implements Converter<String, DecodedToken> {

	@Autowired TokenService tokenService;

	@Override
	public DecodedToken convert(@NotNull String token){

		return Pattern.compile("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*").matcher(token).matches() ?
			tokenService.getDecodedJWT(token) :
			tokenService.getDecodedJWT("");
	}
}

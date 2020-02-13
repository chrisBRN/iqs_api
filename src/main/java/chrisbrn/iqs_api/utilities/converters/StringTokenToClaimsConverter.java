package chrisbrn.iqs_api.utilities.converters;

import chrisbrn.iqs_api.models.api.DecodedToken;
import chrisbrn.iqs_api.services.authentication.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringTokenToClaimsConverter implements Converter<String, DecodedToken> {

	private TokenService tokenService;

	@Autowired
	public StringTokenToClaimsConverter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public DecodedToken convert(String token) {
		return tokenService.getDecodedJWT(token);
	}
}
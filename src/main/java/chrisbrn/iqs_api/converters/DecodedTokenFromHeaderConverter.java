package chrisbrn.iqs_api.converters;


import chrisbrn.iqs_api.constants.Role;
import chrisbrn.iqs_api.models.in.DecodedToken;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DecodedTokenFromHeaderConverter implements Converter<String, DecodedToken> {

	@Autowired private TokenService tokenService;

	@Override
	public DecodedToken convert(String token){

		try {
			return tokenService.getDecodedJWT(token);
		}
 		catch (Exception e){
			return new DecodedToken("", Role.NO_ROLE, "");
		}
	}
}

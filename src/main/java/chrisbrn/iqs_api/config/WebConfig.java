package chrisbrn.iqs_api.config;

import chrisbrn.iqs_api.utilities.converters.StringTokenToClaimsConverter;
import chrisbrn.iqs_api.services.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	TokenService tokenService;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringTokenToClaimsConverter(tokenService));
	}
}
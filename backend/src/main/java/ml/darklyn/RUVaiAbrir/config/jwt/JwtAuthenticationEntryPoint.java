package ml.darklyn.RUVaiAbrir.config.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final String UNATHORIZED_MESSAGE = "Desculpe, você não tem autorização para acessar este recurso.";

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, 
			AuthenticationException e) throws IOException, ServletException {
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNATHORIZED_MESSAGE);
		
	}

}

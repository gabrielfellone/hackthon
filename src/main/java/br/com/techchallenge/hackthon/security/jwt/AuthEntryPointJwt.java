package br.com.techchallenge.hackthon.security.jwt;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.techchallenge.hackthon.exception.ClienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException,
			RuntimeException, ClienteException {

		final ObjectMapper mapper = new ObjectMapper();
		final Map<String, Object> body = new HashMap<>();

		if(response.getStatus() == 200){
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
			body.put("error", "Unauthorized");
			mapper.writeValue(response.getOutputStream(), body);


		} else {
			body.put("status", response.getStatus());
			mapper.writeValue(response.getOutputStream(), body);

		}

	}

}
